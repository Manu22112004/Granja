import { API_BASE_URL } from "./config.js";

/* =========================
   UTILS
========================= */

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

/* =========================
   LOAD INFO
========================= */

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

/* =========================
   KPI
========================= */

async function loadProductionKpis(workConsolidationId) {

    // 1️⃣ Obtener work consolidation
    const consolidation = await fetchJson(
        `/work-consolidations/${workConsolidationId}`
    );

    // 2️⃣ Cargar company y customer en paralelo
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

/* =========================
   TABLE
========================= */

async function loadProductionMatrix(workConsolidationId) {

    const consolidation = await fetchJson(
        `/work-consolidations/${workConsolidationId}`
    );

    if (!consolidation.production_id) return;

    const production = await fetchJson(
        `/productions/${consolidation.production_id}`
    );

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

    renderProductionMatrix(
        rowsData.filter(Boolean)
    );
}

function renderProductionMatrix(items) {
    const tbody = document.getElementById("productionMatrixBody");
    tbody.innerHTML = "";

    items.forEach(({ worker, wp }) => {
        const tr = document.createElement("tr");

        tr.innerHTML = `
            <td>${worker.first_name} ${worker.last_name}</td>
            <td>${worker.employee_number ?? "—"}</td>
            <td>${wp.beds_assigned ?? 0}</td>
            <td>—</td>
            <td>—</td>
            <td>
                <span class="${worker.active ? "status-active" : "status-inactive"}">
                    ${worker.active ? "Active" : "Inactive"}
                </span>
            </td>
        `;

        tbody.appendChild(tr);
    });
}