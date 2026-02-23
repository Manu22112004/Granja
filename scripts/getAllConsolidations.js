const API_URL = "http://localhost:8082/api/work-consolidations";
const API_URL_CUSTOMERS = "http://localhost:8082/api/customers";

document.addEventListener("DOMContentLoaded", loadConsolidations);

async function loadConsolidations() {
    try {
        const response = await fetch(API_URL);
        const consolidations = await response.json();

        const customersResponse = await fetch(API_URL_CUSTOMERS);
        const customers = await customersResponse.json();

        const container = document.getElementById("consolidationsContainer");
        container.innerHTML = "";

        consolidations.forEach(c => {
            const card = createConsolidationCard(c, customers);
            container.appendChild(card);
        });

    } catch (error) {
        console.error("Error loading consolidations:", error);
    }
}

function createConsolidationCard(c, customers) {
    const div = document.createElement("div");
    div.className = "kpi-card consolidation-card";
    div.style.cursor = "pointer";

    const customer = customers.find(cu => cu.customer_id === c.customer_id);
    const customerName = customer ? customer.name : "Unknown";
    

    div.innerHTML = `
        <span class="kpi-label">${customerName}</span>
        <span class="kpi-value">Fecha: ${formatDate(c.work_date)}</span>
        <span class="kpi-value">Beds: ${c.total_beds_produced}/${c.total_beds_planned}</span>
        <span class="kpi-value">Hours: ${c.total_hours}</span>
        <span class="kpi-value">Cost: $${c.total_cost}</span>
    `;
    
    div.addEventListener("click", () => {
        window.location.href = `consolidationDetails.html?id=${c.work_consolidation_id}`;
    });

    return div;
}

function formatDate(dateString) {
    return new Date(dateString).toLocaleDateString();
}