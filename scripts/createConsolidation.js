import { API_BASE_URL } from "./config.js";

/*=============
    INIT VAR
=============*/
const API_CONSOLIDATION = `${API_BASE_URL}/work-consolidations`;
const API_COMPANIES = `${API_BASE_URL}/companies`;
const API_CUSTOMERS = `${API_BASE_URL}/customers`;
const API_CREW_LEADERS = `${API_BASE_URL}/crew-leaders`;
const API_QUALITY_CHECKERS = `${API_BASE_URL}/quality-checkers`;

/*========================
    CONSOLIDATION MODAL
========================*/
document.addEventListener("DOMContentLoaded", () => {
    const modal = document.getElementById("consolidationModal");
    const overlay = document.getElementById("modalOverlay");

    //OPEN MODAL
    window.openModal = function () {
        modal.classList.remove("hidden");
        overlay.classList.remove("hidden");
        loadSelectData();
    };

    //CLOSE MODAL
    window.closeModal = function () {
        modal.classList.add("hidden");
        overlay.classList.add("hidden");
    };

    //LOAD DEFAULT DATA
    async function loadSelectData() {

        const companySelect = document.getElementById("companySelect");
        const customerSelect = document.getElementById("customerSelect");
        const crewLeaderSelect = document.getElementById("crewLeaderSelect");
        const qualityCheckerSelect = document.getElementById("qualityCheckerSelect");

        companySelect.innerHTML = "";
        customerSelect.innerHTML = "";
        crewLeaderSelect.innerHTML = "";
        qualityCheckerSelect.innerHTML = "";

        try {
            const [
                companies,
                customers,
                crewLeaders,
                qualityCheckers
            ] = await Promise.all([
                fetch(API_COMPANIES).then(r => r.json()),
                fetch(API_CUSTOMERS).then(r => r.json()),
                fetch(API_CREW_LEADERS).then(r => r.json()),
                fetch(API_QUALITY_CHECKERS).then(r => r.json())
            ]);

            companies.forEach(c => {
                companySelect.appendChild(
                    new Option(c.name, c.company_id)
                );
            });

            customers.forEach(c => {
                customerSelect.appendChild(
                    new Option(c.name, c.customer_id)
                );
            });

            crewLeaderSelect.appendChild(new Option("—", ""));
            qualityCheckerSelect.appendChild(new Option("—", ""));

            crewLeaders.forEach(p => {
                crewLeaderSelect.appendChild(
                    new Option(`${p.first_name} ${p.last_name}`, p.person_id)
                );
            });

            qualityCheckers.forEach(p => {
                qualityCheckerSelect.appendChild(
                    new Option(`${p.first_name} ${p.last_name}`, p.person_id)
                );
            });

        } catch (error) {
            console.error("Error loading select data:", error);
        }
    }

    //SUBMIT DATA
    document.getElementById("consolidationForm")
        .addEventListener("submit", async (e) => {

            e.preventDefault();

            const today = new Date();
            today.setMinutes(today.getMinutes() - today.getTimezoneOffset());
            const workDate = today.toISOString().split("T")[0];

            const crewLeaderValue =
                document.getElementById("crewLeaderSelect").value || null;
            const qualityCheckerValue =
                document.getElementById("qualityCheckerSelect").value || null;

            const payload = {
                workDate,
                pullType: "Primera",
                maxTime: Number(document.getElementById("maxTime").value),
                totalHours: 0,
                totalBedsPlanned: Number(document.getElementById("totalBedsPlanned").value),
                totalBedsProduced: 0,
                totalCost: 0,
                companyId: document.getElementById("companySelect").value,
                customerId: document.getElementById("customerSelect").value,
                productionId: null,
                productionMatrixId: null,
                productionReportId: null,
                crewLeaderId: crewLeaderValue,
                qualityCheckerId: qualityCheckerValue
            };

            try {
                const response = await fetch(API_CONSOLIDATION, {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(payload)
                });

                const responseText = await response.text();

                if (!response.ok) {
                    throw new Error(responseText);
                }

                closeModal();
                if (typeof loadConsolidations === "function") {
                    loadConsolidations();
                }

            } catch (err) {
                console.error("Error creating consolidation:", err);
            }
    });
});