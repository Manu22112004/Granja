let currentConsolidation = null;

const API_BASE = "http://localhost:8082/api";
const API_URL = `${API_BASE}/work-consolidations`;

/* =========================
   INIT
========================= */

document.addEventListener("DOMContentLoaded", async () => {
    enableKpiSelection();
    await loadConsolidationDetail();
    setupModal();
});

/* =========================
   LOAD CONSOLIDATION
========================= */

async function loadConsolidationDetail() {
    const id = new URLSearchParams(window.location.search).get("id");
    if (!id) return;

    try {
        const response = await fetch(`${API_URL}/${id}`);
        if (!response.ok) throw new Error("Failed to load consolidation");

        currentConsolidation = await response.json();

        // Helper: fetch solo si hay id, si no devuelve null
        const safeFetch = (url, id) => id ? fetchJson(`${url}/${id}`) : Promise.resolve(null);

        const [
            company,
            customer,
            crewLeader,
            qualityChecker,
            pricingPolicy
        ] = await Promise.all([
            safeFetch("/companies", currentConsolidation.company_id),
            safeFetch("/customers", currentConsolidation.customer_id),
            safeFetch("/crew-leaders", currentConsolidation.crew_leader_id),
            safeFetch("/quality-checkers", currentConsolidation.quality_checker_id),
            safeFetch("/pricing-policies", currentConsolidation.pricing_policy_id)
        ]);

        renderKpis(
            company ?? "-",
            customer ?? "-",
            crewLeader ?? "-",
            qualityChecker ?? "-",
            pricingPolicy ?? "-"
        );

        if (currentConsolidation.production_id) {
            await loadWorkersFromProduction(currentConsolidation.production_id);
        }

    } catch (error) {
        console.error("Error loading consolidation:", error);
    }
}

function renderKpis(company, customer, crewLeader, qualityChecker, pricingPolicy) {
    document.getElementById("companyName").textContent = company.name;
    document.getElementById("customerName").textContent = customer.name;
    document.getElementById("crewLeader").textContent =
        `${crewLeader.first_name} ${crewLeader.last_name}`;
    document.getElementById("qualityChecker").textContent =
        `${qualityChecker.first_name} ${qualityChecker.last_name}`;

    document.getElementById("bedsProduced").textContent =
        currentConsolidation.total_beds_produced;

    document.getElementById("startDate").textContent =
        formatDate(currentConsolidation.work_date);

    document.getElementById("priceHour").textContent =
    pricingPolicy ? `$${pricingPolicy.price_per_hour}` : "—";

    document.getElementById("priceBed").textContent =
    pricingPolicy ? `$${pricingPolicy.price_per_bed}` : "—";
}

function formatDate(dateString) {
    return new Date(dateString).toLocaleDateString();
}

async function fetchJson(endpoint) {
    const response = await fetch(`${API_BASE}${endpoint}`);
    if (!response.ok) throw new Error(`Error fetching ${endpoint}`);
    return response.json();
}

function enableKpiSelection() {
    document.querySelectorAll(".kpi-card").forEach(card => {
        card.addEventListener("click", () => {
            const label = card.querySelector(".kpi-label").textContent;
            const value = card.querySelector(".kpi-value").textContent;
            alert(`Seleccionaste:\n${label}: ${value}`);
        });
    });
}

/* =========================
   LOAD WORKERS
========================= */

async function loadWorkersFromProduction(productionId) {
    try {
        const production = await fetchJson(`/productions/${productionId}`);
        const workerProductionIds = production.workerProductions || [];

        const workerProductions = await Promise.all(
            workerProductionIds.map(id =>
                fetchJson(`/worker-productions/${id}`)
            )
        );

        const validWorkerProductions = workerProductions.filter(
            wp => wp.worker_id !== null && wp.worker_id !== undefined
        );

        const workers = await Promise.all(
            validWorkerProductions.map(wp =>
                fetchJson(`/workers/${wp.worker_id}`)
            )
        );

        renderWorkers(workers);

    } catch (error) {
        console.error("Error loading workers:", error);
    }
}

function renderWorkers(workers) {
    const tbody = document.getElementById("workersTableBody");
    tbody.innerHTML = "";

    workers.forEach((worker, index) => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${index + 1}</td>
            <td>${worker.first_name} ${worker.last_name}</td>
        `;

        row.addEventListener("click", () => {
            alert(`Worker seleccionado:\n${worker.first_name} ${worker.last_name}`);
        });

        tbody.appendChild(row);
    });

    document.getElementById("workersTitle").textContent =
        `👷 ${workers.length} Workers Active`;
}