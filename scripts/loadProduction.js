import { API_BASE_URL } from "./config.js";

let productionMatrixData = [];

/*=========
   UTILS
=========*/
async function fetchJson(endpoint) {
    const response = await fetch(`${API_BASE_URL}${endpoint}`);
    if (!response.ok) {
        throw new Error(`Error fetching ${endpoint}`);
    }
    return response.json();
}

function formatDate(dateString) {
    return new Date(dateString).toLocaleDateString();
}

async function patchJson(endpoint, body) {
    const response = await fetch(`${API_BASE_URL}${endpoint}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body)
    });

    if (!response.ok) {
        throw new Error(`Error patching ${endpoint}`);
    }

    return response.json();
}

/*=============
   INIT PAGE
=============*/
document.addEventListener("DOMContentLoaded", async () => {
    const params = new URLSearchParams(window.location.search);
    const workConsolidationId = params.get("production_id");

    if (!workConsolidationId) {
        alert("Work consolidation ID not found");
        return;
    }

    try {
        await loadProductionKpis(workConsolidationId);
        await loadProductionMatrix(workConsolidationId);
    } catch (error) {
        console.error("Error loading production data:", error);
        alert("Error loading production data");
    }
});

/*===========================
   LOAD KPI FOR PRODUCTION
===========================*/

async function loadProductionKpis(workConsolidationId) {

    const consolidation = await fetchJson(
        `/work-consolidations/${workConsolidationId}`
    );

    const [company, customer] = await Promise.all([
        consolidation.company_id
            ? fetchJson(`/companies/${consolidation.company_id}`)
            : null,
        consolidation.customer_id
            ? fetchJson(`/customers/${consolidation.customer_id}`)
            : null
    ]);

    document.getElementById("kpi-work-date").textContent =
        consolidation.work_date
            ? formatDate(consolidation.work_date)
            : "—";

    document.getElementById("kpi-company").textContent =
        company?.name ?? "—";

    document.getElementById("kpi-acres").textContent =
        consolidation.total_beds_planned ?? "—";

    document.getElementById("kpi-beds").textContent =
        consolidation.total_beds_produced ?? "—";

    document.getElementById("kpi-bonuses").textContent = "—";

    document.getElementById("kpi-pull").textContent =
        consolidation.pull_type ?? "—";

    document.getElementById("kpi-customer").textContent =
        customer?.name ?? "—";

    document.getElementById("kpi-max-time").textContent =
        consolidation.max_time ?? "—";
}

/*========================
   LOAD PRODUCTION DATA
========================*/
async function loadProductionMatrix(workConsolidationId) {

    const consolidation = await fetchJson(
        `/work-consolidations/${workConsolidationId}`
    );

    if (!consolidation.production_id) return;

    const production = await fetchJson(
        `/productions/${consolidation.production_id}`
    );

    if(!production.production_id) return;

    const workerProductionIds = production.workerProductions || [];

    const tbody = document.getElementById("productionMatrixBody");
    tbody.innerHTML = "";

    if (workerProductionIds.length === 0) {
        tbody.innerHTML =
            `<tr><td colspan="6">No workers assigned</td></tr>`;
        return;
    }

    const workerProductions = await Promise.all(
        workerProductionIds.map(id =>
            fetchJson(`/worker-productions/${id}`)
        )
    );

    const rowsData = await Promise.all(
        workerProductions.map(async wp => {
            if (!wp.worker_id) return null;

            const worker = await fetchJson(`/workers/${wp.worker_id}`);
            return { worker, wp };
        })
    );

    const sortedWorkers = rowsData
        .filter(Boolean)
        .sort((a, b) => {
            const numA = parseInt(a.worker.employee_number) || 0;
            const numB = parseInt(b.worker.employee_number) || 0;
            return numA - numB;
        });

    renderProductionMatrix(sortedWorkers);
}

//UPDATE BONUS KPIS
function updateBonusKpi() {

    const totalBonus = productionMatrixData.reduce((sum, item) => {
        return sum + (parseFloat(item.wp.bonus_assigned) || 0);
    }, 0);

    document.getElementById("kpi-bonuses").textContent = totalBonus;
}

/*=================================
    LOAD TABLE PRODUCTION MATRIX
=================================*/
function renderProductionMatrix(items) {
    const tbody = document.getElementById("productionMatrixBody");
    tbody.innerHTML = "";

    productionMatrixData = items;
    updateBonusKpi();
    items.forEach(({ worker, wp }) => {
        const tr = document.createElement("tr");

        tr.innerHTML = `
            <td>${worker.first_name} ${worker.last_name}</td>
            <td>${worker.employee_number ?? "—"}</td>
            <td>${wp.beds_assigned ?? 0}</td>
            <td>
                <input 
                    type="number" 
                    class="bonus-input"
                    value="${wp.bonus_assigned ?? 0}"
                    step="0.5"
                    data-worker-production-id="${wp.worker_production_id}"
                >
            </td>
            <td class="total-beds">${(wp.beds_assigned ?? 0) + (wp.bonus_assigned ?? 0)}</td>
            <td>
                <span class="${worker.active ? "status-active" : "status-inactive"}">
                    ${worker.active ? "Active" : "Inactive"}
                </span>
            </td>
        `;

        tbody.appendChild(tr);
    });
    const bonusInputs = document.querySelectorAll(".bonus-input");

    bonusInputs.forEach(input => {
    input.addEventListener("input", async () => {
        const row = input.closest("tr");
        const workerProductionId = input.dataset.workerProductionId;
        const item = productionMatrixData.find(
            i => i.wp.worker_production_id === workerProductionId
        );

        if (!item) return;

        const bonus = parseFloat(input.value) || 0;
        const bedsAssigned = item.wp.beds_assigned || 0;
        const totalBeds = bedsAssigned + bonus;

        // Actualiza UI
        const totalCell = row.querySelector(".total-beds");
        totalCell.textContent = totalBeds;

        // Actualiza datos en memoria
        item.wp.bonus_assigned = bonus;
        item.wp.total_beds = totalBeds;

        try {
            // Persiste cambios en backend
            await patchJson(`/worker-productions/${workerProductionId}`, {
                bedsAssigned: bedsAssigned,
                bonusAssigned: bonus,
                totalBeds: totalBeds,
                workerId: item.worker.person_id,
                productionId: item.wp.production_id
            });
        } catch (error) {
            console.error("Error saving bonus:", error);
        }

        updateBonusKpi();
    });
});

}

/*=================================================
    UPDATE BEDS AND NUM FOR WORKER IN PRODUCTION
=================================================*/
const editBtn = document.getElementById("editProductionBtn");
const modal = document.getElementById("editProductionModal");
const container = document.getElementById("editWorkersContainer");
const startingInput = document.getElementById("startingNumberInput");

editBtn.addEventListener("click", () => {
    openEditModal();
});

document.getElementById("cancelEditProduction")
    .addEventListener("click", () => {
        modal.style.display = "none";
    });

document.getElementById("saveEditProduction")
    .addEventListener("click", () => {
        applyChangesFromModal();
        modal.style.display = "none";
    });


function openEditModal() {

    container.innerHTML = `
        <div style="margin-bottom:16px;">
            <label>New Beds Assigned (for ALL workers)</label>
            <input type="number" id="globalBedsInput" />
        </div>
    `;

    modal.style.display = "flex";
}
//SAVE CHANGES
async function applyChangesFromModal() {

    const newStartingNumber = parseInt(startingInput.value);
    const newBedsAssigned = parseFloat(
        document.getElementById("globalBedsInput")?.value
    );

    try {

        const rows = document.querySelectorAll("#productionMatrixBody tr");

        for (let i = 0; i < productionMatrixData.length; i++) {

            const item = productionMatrixData[i];
            const row = rows[i];

            const bonusInput = row.querySelector(".bonus-input");
            const bonus = parseFloat(bonusInput?.value) || 0;

            const bedsAssigned = !isNaN(newBedsAssigned)
                ? newBedsAssigned
                : item.wp.beds_assigned;

            const totalBeds = bedsAssigned + bonus;

            // ===============================
            // UPDATE WORKER PRODUCTION
            // ===============================
            await patchJson(
                `/worker-productions/${item.wp.worker_production_id}`,
                {
                    bedsAssigned: bedsAssigned,
                    bonusAssigned: bonus,
                    totalBeds: totalBeds,
                    workerId: item.worker.person_id,
                    productionId: item.wp.production_id
                }
            );

            // ===============================
            // UPDATE EMPLOYEE NUMBER (SEQUENCE)
            // ===============================
            if (!isNaN(newStartingNumber)) {

                await patchJson(
                    `/workers/${item.worker.person_id}`,
                    {
                        firstName: item.worker.first_name,
                        lastName: item.worker.last_name,
                        active: item.worker.active,
                        employeeNumber: String(newStartingNumber + i),
                        skillLevel: item.worker.skill_level,
                        hourlyRate: item.worker.hourly_rate
                    }
                );
            }
        }

        const params = new URLSearchParams(window.location.search);
        const workConsolidationId = params.get("production_id");

        await loadProductionMatrix(workConsolidationId);

        modal.style.display = "none";

        console.log("Production updated successfully");

    } catch (error) {
        console.error("Error updating production:", error);
        alert("Error updating production");
    }
}