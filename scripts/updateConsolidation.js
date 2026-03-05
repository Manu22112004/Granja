import { API_BASE_URL } from "./config.js";

/*=======================
    INIT CONSOLIDATION
=======================*/
const WORK_CONSOLIDATIONS_URL = `${API_BASE_URL}/work-consolidations`;

/* =======================================
   UPDATE CONSOLIDATION MODAL FUNCTIONS
========================================*/
window.setupModal = function() {

    const modal = document.getElementById("modalConsolidation");
    const openBtn = document.getElementById("editConsolidationBtn");
    const closeBtn = document.getElementById("closeModalConsolidation");
    const saveBtn = modal.querySelector(".save-btn");

    //OPEN MODAL AND LOAD CURRENT CONSOLIDATION DATA
    openBtn.addEventListener("click", async () => {
        if (!window.currentConsolidation) return;
        
        await loadSelectOptions();

        document.getElementById("edit_work_date").value =
            window.currentConsolidation.work_date?.split("T")[0];

        document.getElementById("edit_total_beds_produced").value =
            window.currentConsolidation.total_beds_produced;

        setSelectValue("edit_company_id", window.currentConsolidation.company_id);
        setSelectValue("edit_customer_id", window.currentConsolidation.customer_id);
        setSelectValue("edit_crew_leader_id", window.currentConsolidation.crew_leader_id);
        setSelectValue("edit_quality_checker_id", window.currentConsolidation.quality_checker_id);

        modal.style.display = "flex";
    });

    //CLOSE MODAL
    closeBtn.addEventListener("click", () => {
        modal.style.display = "none";
    });

    //SAVE UPDATED CONSOLIDATION
    saveBtn.addEventListener("click", async () => {

        if (!window.currentConsolidation) return;

        const updatedData = {
            workConsolidationId: window.currentConsolidation.work_consolidation_id,
            workDate: document.getElementById("edit_work_date").value,
            
            totalBedsProduced: Number(
                document.getElementById("edit_total_beds_produced").value
            ),
            companyId: getSafeValue("edit_company_id"),
            customerId: getSafeValue("edit_customer_id"),
            crewLeaderId: getSafeValue("edit_crew_leader_id"),
            qualityCheckerId: getSafeValue("edit_quality_checker_id"),

            pullType: window.currentConsolidation.pull_type,
            maxTime: window.currentConsolidation.max_time,
            totalHours: window.currentConsolidation.total_hours,
            totalBedsPlanned: window.currentConsolidation.total_beds_planned,
            totalCost: window.currentConsolidation.total_cost
        };

        //TRY PUT REQUEST TO UPDATE CONSOLIDATION
        try {
            const payload = cleanPayload(updatedData);

            const response = await fetch(
                `${WORK_CONSOLIDATIONS_URL}/${window.currentConsolidation.work_consolidation_id}`,
                {
                    method: "PUT",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(payload)
                }
            );

            if (!response.ok) {
                const errorText = await response.text();
                console.error("Backend error:", errorText);
                throw new Error("Update failed");
            }

            await loadConsolidationDetail();
            modal.style.display = "none";

        } catch (error) {
            console.error("Error updating consolidation:", error);
            alert("Error saving changes");
        }
    });

    modal.addEventListener("click", (e) => {
        if (e.target === modal) modal.style.display = "none";
    });

    document.addEventListener("keydown", (e) => {
        if (e.key === "Escape") modal.style.display = "none";
    });
}

/* =========================
   HELPERS
========================= */

function setSelectValue(selectId, value) {
    const select = document.getElementById(selectId);
    if (!select) return;

    if (value === null || value === undefined) {
        select.value = "";
        return;
    }

    select.value = String(value);
}

function getSafeValue(selectId) {
    const select = document.getElementById(selectId);
    if (!select) return null;

    const value = select.value;
    return value ? value : null;
}

/* =========================
   SELECT OPTIONS
========================= */

async function loadSelectOptions() {
    const [companies, customers, crewLeaders, qualityCheckers] =
        await Promise.all([
            fetchJson("/companies"),
            fetchJson("/customers"),
            fetchJson("/crew-leaders"),
            fetchJson("/quality-checkers")
        ]);

    fillSelect("edit_company_id", companies, "name", false, "company_id");
    fillSelect("edit_customer_id", customers, "name", false, "customer_id");
    fillSelect("edit_crew_leader_id", crewLeaders, null, true, "person_id");
    fillSelect("edit_quality_checker_id", qualityCheckers, null, true, "person_id");
}

function fillSelect(selectId, data, field, isPerson = false, idField) {
    const select = document.getElementById(selectId);
    select.innerHTML = "";

    const emptyOption = document.createElement("option");
    emptyOption.value = "";
    emptyOption.textContent = "—";
    select.appendChild(emptyOption);

    data.forEach(item => {
        const option = document.createElement("option");
        option.value = item[idField];
        option.textContent = isPerson
            ? `${item.first_name} ${item.last_name}`
            : item[field];
        select.appendChild(option);
    });
}

/* =========================
   UTILITIES
========================= */

function cleanPayload(obj) {
    return Object.fromEntries(
        Object.entries(obj).filter(([_, value]) => value !== null)
    );
}

async function fetchJson(endpoint) {
    const response = await fetch(`${API_BASE_URL}${endpoint}`);
    if (!response.ok) {
        throw new Error(`Error fetching ${endpoint}`);
    }
    return response.json();
}