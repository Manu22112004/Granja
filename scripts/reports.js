import { API_BASE_URL } from "./config.js"

let reportsData = []
//  UTILS
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

//  INIT PAGE
document.addEventListener("DOMContentLoaded", async () => {
    const params = new URLSearchParams(window.location.search);
    const workConsolidationId = params.get("production_report_id");

    if (!workConsolidationId) {
        alert("Work consolidation ID not found");
        return;
    }

    try {
        await loadReportKpis(workConsolidationId);
        await loadReports(workConsolidationId)
    } catch (error) {
        console.error("Error loading reports data:", error);
        alert("Error loading reports data");
    }
});

//  LOAD KPIS
async function loadReportKpis(workConsolidationId) {

    const consolidation = await fetchJson(
        `/work-consolidations/${workConsolidationId}`
    );

    const [company, customer, crewLeader, qualityChecker] = await Promise.all([
        consolidation.company_id
            ? fetchJson(`/companies/${consolidation.company_id}`)
            : null,
        consolidation.customer_id
            ? fetchJson(`/customers/${consolidation.customer_id}`)
            : null,
        consolidation.crew_leader_id 
            ? fetchJson(`/crew-leaders/${consolidation.crew_leader_id}`)
            : null,
        consolidation.quality_checker_id
            ? fetchJson(`/quality-checkers/${consolidation.quality_checker_id}`)
            : null
    ]);

    document.getElementById("kpi-company-name").textContent =
        company?.name ?? "";

    document.getElementById("kpi-customer-name").textContent =
        customer?.name ?? "—";

    document.getElementById("kpi-date").textContent =
        consolidation.work_date
            ? formatDate(consolidation.work_date)
            : " ";
    
    document.getElementById("kpi-pull").textContent =
        consolidation.pull_type ?? "—";

    document.getElementById("kpi-num-farm").textContent =
        consolidation.total_beds_produced ?? "ERROR";

    document.getElementById("kpi-planned-beds").textContent = 
        consolidation.total_beds_planned ?? "—";

    document.getElementById("kpi-crew-leader-name").textContent =
        `${crewLeader.first_name} ${crewLeader.last_name}` ?? "—";

    document.getElementById("kpi-quality-checker-name").textContent =
        `${qualityChecker.first_name} ${qualityChecker.last_name}` ?? "—";
}

//  CARGAR DATOS EN LA TABLA
async function loadReports(workConsolidationId) {
    const consolidation = await fetchJson(
        `/work-consolidations/${workConsolidationId}`
    )

    if(!consolidation.production_report_id) return

    const report = await fetchJson(
        `/production-reports/${consolidation.production_report_id}`
    )

    if(!report.production_report_id) return;

    const production = await fetchJson(
        `/productions/${consolidation.production_id}`
    );

    if(!production.production_id) return;

    const workerProductionIds = production.workerProductions || [];

    const tbody = document.getElementById("reportsTableBody")
    tbody.innerHTML=""

    if(workerProductionIds.length === 0){
        tbody.innerHTML = 
            `<tr><td colspan="6">No workers assigned</td></tr>`
        return
    }

    const workerProductions = await Promise.all(
        workerProductionIds.map(id => 
            fetchJson(`/worker-productions/${id}`)
        )
    )

    const rowsData = await Promise.all(
        workerProductions.map(async wp =>{
            if(!wp.worker_id) return null
            const worker = await fetchJson(`/workers/${wp.worker_id}`)
            return {worker, wp}
        })
    )

    const sortedWorkers = rowsData
        .filter(Boolean)
        .sort((a,b) => {
            const numA = parseInt(a.worker.employee_number) || 0
            const numB = parseInt(b.worker.employee_number) || 0
            return numA - numB
        })
    
    renderReports(sortedWorkers);
}

function renderReports(items){
    const tbody = document.getElementById("reportsTableBody")
    tbody.innerHTML = ""

    reportsData = items;
    items.forEach(({worker,wp}) => {
        const tr = document.createElement("tr")

        const initials = "" + String(worker.first_name).charAt(0).toUpperCase() + String(worker.last_name).charAt(0).toUpperCase()

        tr.innerHTML = `
            <td>${worker.first_name} ${worker.last_name}</td>
            <td>${worker.employee_number}</td>
            <td>${wp.total_beds}</td>
            <td>${initials}</td>
        `

        tbody.appendChild(tr);
    })
}

//export
import { convertReportToPDF } from "../utils/convertjspdf.js";

document.getElementById("btnExport").addEventListener("click", () => {
    convertReportToPDF("reportTable"
        ,"kpi-company-name"
        ,"kpi-date"
        ,"kpi-pull"
        ,"kpi-customer-name"
        ,"kpi-num-farm"
        ,"kpi-planned-beds"
        ,"kpi-crew-leader-name"
        ,"kpi-quality-checker-name"
    );
});