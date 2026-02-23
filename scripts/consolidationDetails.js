const API_BASE = "http://localhost:8082/api";
const API_URL = `${API_BASE}/work-consolidations`;

document.addEventListener("DOMContentLoaded", loadConsolidationDetail);
document.addEventListener("DOMContentLoaded", enableKpiSelection);

async function loadConsolidationDetail() {
    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");

    if (!id) return;

    try {
        // 1️⃣ Traer consolidation
        const response = await fetch(`${API_URL}/${id}`);
        const consolidation = await response.json();

        console.log("Consolidation:", consolidation);

        // 2️⃣ Traer datos relacionados en paralelo
        const [
            companyRes,
            customerRes,
            crewLeaderRes,
            qualityCheckerRes,
            pricingPolicyRes
        ] = await Promise.all([
            fetch(`${API_BASE}/companies/${consolidation.company_id}`),
            fetch(`${API_BASE}/customers/${consolidation.customer_id}`),
            fetch(`${API_BASE}/crew-leaders/${consolidation.crew_leader_id}`),
            fetch(`${API_BASE}/quality-checkers/${consolidation.quality_checker_id}`),
            fetch(`${API_BASE}/pricing-policies/${consolidation.pricing_policy_id}`)
        ]);

        const company = await companyRes.json();
        const customer = await customerRes.json();
        const crewLeader = await crewLeaderRes.json();
        const qualityChecker = await qualityCheckerRes.json();
        const pricingPolicy = await pricingPolicyRes.json();

        // 3️⃣ Insertar datos en los KPI

        document.getElementById("companyName").textContent = company.name;
        document.getElementById("customerName").textContent = customer.name;
        document.getElementById("crewLeader").textContent = `${crewLeader.first_name} ${crewLeader.last_name}`;
        document.getElementById("qualityChecker").textContent = `${qualityChecker.first_name} ${qualityChecker.last_name}`;

        document.getElementById("bedsProduced").textContent =
            consolidation.total_beds_produced;

        document.getElementById("startDate").textContent =
            formatDate(consolidation.work_date);

        document.getElementById("priceHour").textContent =
            `$${pricingPolicy.price_per_hour}`;

        document.getElementById("priceBed").textContent =
            `$${pricingPolicy.price_per_bed}`;

        await loadWorkersFromProduction(consolidation.production_id);

    } catch (error) {
        console.error("Error loading consolidation:", error);
    }
}

function enableKpiSelection() {

    const cards = document.querySelectorAll(".kpi-card");

    cards.forEach(card => {

        // Evento click
        card.addEventListener("click", () => {
            const label = card.querySelector(".kpi-label").textContent;
            const value = card.querySelector(".kpi-value").textContent;

            alert(`Seleccionaste:\n${label}: ${value}`);
        });

    });
}

async function loadWorkersFromProduction(productionId) {

    try {

        // 1️⃣ Traer production
        const productionRes = await fetch(
            `${API_BASE}/productions/${productionId}`
        );

        const production = await productionRes.json();

        const workerProductionIds = production.workerProductions || [];

        // 2️⃣ Traer todas las workerProductions en paralelo
        const workerProductionResponses = await Promise.all(
            workerProductionIds.map(id =>
                fetch(`${API_BASE}/worker-productions/${id}`)
            )
        );

        const workerProductions = await Promise.all(
            workerProductionResponses.map(res => res.json())
        );

        // 3️⃣ Traer todos los workers en paralelo
        const workerResponses = await Promise.all(
            workerProductions.map(wp =>
                fetch(`${API_BASE}/workers/${wp.worker_id}`)
            )
        );

        const workers = await Promise.all(
            workerResponses.map(res => res.json())
        );

        // 4️⃣ Pintar tabla
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

        // 5️⃣ Actualizar contador
        document.getElementById("workersTitle").textContent =
            `👷 ${workers.length} Workers Active`;

    } catch (error) {
        console.error("Error loading workers:", error);
    }
}

function formatDate(dateString) {
    return new Date(dateString).toLocaleDateString();
}