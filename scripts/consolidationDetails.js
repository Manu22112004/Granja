import { API_BASE_URL } from "./config.js";

let currentConsolidation = null;
let currentWorkers = [];
let allWorkersCache = [];
let work_consolidation_id = null;

// Endpoints base
const WORK_CONSOLIDATIONS_URL = `${API_BASE_URL}/work-consolidations`;

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

window.loadConsolidationDetail = async function () {
    const id = new URLSearchParams(window.location.search).get("id");
    if (!id) return;
    work_consolidation_id = id;

    try {
        const response = await fetch(`${WORK_CONSOLIDATIONS_URL}/${id}`);        if (!response.ok) throw new Error("Failed to load consolidation");

        currentConsolidation = await response.json();
        window.currentConsolidation = currentConsolidation;

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
    const response = await fetch(`${API_BASE_URL}${endpoint}`);
    if (!response.ok) {
        throw new Error(`Error fetching ${endpoint}`);
    }
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
    currentWorkers = workers;
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

//Tabla workers

document.getElementById("addWorkerBtn").addEventListener("click", async () => {
    await openAddWorkersModal();
});

async function openAddWorkersModal() {
    const modal = document.getElementById("addWorkersModal");
    const list = document.getElementById("availableWorkersList");

    modal.classList.remove("hidden-workers");
    list.innerHTML = "Loading workers...";

    if (allWorkersCache.length === 0) {
        allWorkersCache = await fetchJson("/workers");
    }

    const activeIds = new Set(currentWorkers.map(w => w.person_id));

    const availableWorkers = allWorkersCache.filter(
        w => !activeIds.has(w.person_id)
    );

    if (availableWorkers.length === 0) {
        list.innerHTML = "<p>No available workers</p>";
        return;
    }

    list.innerHTML = "";

    availableWorkers.forEach(worker => {
        const div = document.createElement("div");
        div.className = "worker-item-workers";

        div.innerHTML = `
            <label>
                <input type="checkbox" value="${worker.person_id}">
                ${worker.first_name} ${worker.last_name}
            </label>
        `;

        list.appendChild(div);
    });
}

document.getElementById("closeAddWorkersModal")
    .addEventListener("click", closeAddWorkersModal);

function closeAddWorkersModal() {
    document.getElementById("addWorkersModal")
        .classList.add("hidden-workers");
}

document.getElementById("confirmAddWorkersBtn")
    .addEventListener("click", async () => {

    const checked = document.querySelectorAll(
        "#availableWorkersList input[type='checkbox']:checked"
    );

    if (checked.length === 0) {
        alert("Select at least one worker");
        return;
    }

    const workerIds = Array.from(checked).map(cb => cb.value);

    //sds
    try {
        // 🔴 AJUSTA este endpoint según tu backend
        const productionId = currentConsolidation.production_id;

        for (const workerId of workerIds) {
            await fetch(`${API_BASE_URL}/worker-productions`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    productionId: productionId,
                    workerId: workerId,
                    bedsAssigned: 0,
                    bonusApplied: false
                })
            });
        }

        closeAddWorkersModal();

        // recargar workers activos
        await loadWorkersFromProduction(currentConsolidation.production_id);

    } catch (error) {
        console.error("Error adding workers:", error);
        alert("Error adding workers");
    }
});

const goToProductionBtn = document.getElementById("goToProductionBtn");

if (goToProductionBtn) {
    goToProductionBtn.addEventListener("click", () => {
        if (!work_consolidation_id) {
            alert("Work consolidation ID not found");
            return;
        }

        // Redirige enviando el ID como production_id
        window.location.href = `production.html?production_id=${work_consolidation_id}`;
    });
}