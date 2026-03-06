import { API_BASE_URL } from "./config.js"

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
