document.addEventListener("DOMContentLoaded", () => {

    const API_CONSOLIDATION = "http://localhost:8082/api/work-consolidations";
    const API_COMPANIES = "http://localhost:8082/api/companies";
    const API_CUSTOMERS = "http://localhost:8082/api/customers";
    const API_CREW_LEADERS = "http://localhost:8082/api/crew-leaders";
    const API_QUALITY_CHECKERS = "http://localhost:8082/api/quality-checkers";

    const modal = document.getElementById("consolidationModal");
    const overlay = document.getElementById("modalOverlay");

    /* =========================
       OPEN / CLOSE MODAL
    ========================= */
    window.openModal = function () {
        modal.classList.remove("hidden");
        overlay.classList.remove("hidden");
        loadSelectData();
    }

    window.closeModal = function () {
        modal.classList.add("hidden");
        overlay.classList.add("hidden");
    }

    /* =========================
       LOAD DROPDOWNS
    ========================= */
    async function loadSelectData() {

        const companySelect = document.getElementById("companySelect");
        const customerSelect = document.getElementById("customerSelect");
        const crewLeaderSelect = document.getElementById("crewLeaderSelect");
        const qualityCheckerSelect = document.getElementById("qualityCheckerSelect");
        
        companySelect.innerHTML = "";
        customerSelect.innerHTML = "";
        crewLeaderSelect.innerHTML = "";
        qualityCheckerSelect.innerHTML = "";

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
            const option = document.createElement("option");
            option.value = c.company_id;
            option.textContent = c.name;
            companySelect.appendChild(option);
        });

        customers.forEach(c => {
            const option = document.createElement("option");
            option.value = c.customer_id;
            option.textContent = c.name;
            customerSelect.appendChild(option);
        });

        crewLeaderSelect.appendChild(new Option("—", ""));
        qualityCheckerSelect.appendChild(new Option("—", ""));

        crewLeaders.forEach(p => {
            const option = document.createElement("option");
            option.value = p.person_id;
            option.textContent = `${p.first_name} ${p.last_name}`;
            crewLeaderSelect.appendChild(option);
        });

        qualityCheckers.forEach(p => {
            const option = document.createElement("option");
            option.value = p.person_id;
            option.textContent = `${p.first_name} ${p.last_name}`;
            qualityCheckerSelect.appendChild(option);
        });
    }

    /* =========================
       SUBMIT FORM
    ========================= */
    document.getElementById("consolidationForm")
    .addEventListener("submit", async (e) => {

        e.preventDefault();

        const today = new Date();
        today.setMinutes(today.getMinutes() - today.getTimezoneOffset());
        const workDate = today.toISOString().split("T")[0];

        const crewLeaderValue = document.getElementById("crewLeaderSelect").value;
        const qualityCheckerValue = document.getElementById("qualityCheckerSelect").value;

        const payload = {
            workDate: workDate,
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
            pricingPolicyId: null,
            productionReportId: null,
            crewLeaderId: crewLeaderValue || null,
            qualityCheckerId: qualityCheckerValue || null
        };

        try {
            console.log("Fecha enviada:", workDate);

            const response = await fetch(API_CONSOLIDATION, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload)
            });

            const responseText = await response.text();

            console.log("STATUS:", response.status);
            console.log("RESPONSE:", responseText);

            if (!response.ok) {
                throw new Error(responseText);
            }

            closeModal();
            if (typeof loadConsolidations === "function") {
                loadConsolidations();
            }

        } catch (err) {
            console.error(err);
        }
    });

});