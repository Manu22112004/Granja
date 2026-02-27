/* =========================
   MODAL
========================= */

function setupModal() {
    
    const modal = document.getElementById("modalConsolidation");
    const openBtn = document.getElementById("editConsolidationBtn");
    const closeBtn = document.getElementById("closeModalConsolidation");
    const saveBtn = modal.querySelector(".save-btn");

    openBtn.addEventListener("click", async () => {
        if (!currentConsolidation) return;

        // 1️⃣ Cargar opciones primero
        await loadSelectOptions();

        // 2️⃣ Llenar inputs normales
        document.getElementById("edit_work_date").value =
            currentConsolidation.work_date?.split("T")[0];

        document.getElementById("edit_total_beds_produced").value =
            currentConsolidation.total_beds_produced;

        // 3️⃣ Seleccionar valores actuales correctamente
        setSelectValue("edit_company_id", currentConsolidation.company_id);
        setSelectValue("edit_customer_id", currentConsolidation.customer_id);
        setSelectValue("edit_crew_leader_id", currentConsolidation.crew_leader_id);
        setSelectValue("edit_quality_checker_id", currentConsolidation.quality_checker_id);

        // 4️⃣ Mostrar modal
        modal.style.display = "flex";
    });

    closeBtn.addEventListener("click", () => {
        modal.style.display = "none";
    });

    saveBtn.addEventListener("click", async () => {

        if (!currentConsolidation) return;

        // 🔥 PAYLOAD EN camelCase (lo que espera el backend)
        const updatedData = {
            workConsolidationId: currentConsolidation.work_consolidation_id,

            // Editables
            workDate: document.getElementById("edit_work_date").value,
            totalBedsProduced: Number(
                document.getElementById("edit_total_beds_produced").value
            ),
            companyId: getSafeValue("edit_company_id"),
            customerId: getSafeValue("edit_customer_id"),
            crewLeaderId: getSafeValue("edit_crew_leader_id"),
            qualityCheckerId: getSafeValue("edit_quality_checker_id"),

            // 🔒 No editables pero obligatorios para el backend
            pullType: currentConsolidation.pull_type,
            maxTime: currentConsolidation.max_time,
            totalHours: currentConsolidation.total_hours,
            totalBedsPlanned: currentConsolidation.total_beds_planned,
            totalCost: currentConsolidation.total_cost
        };

        try {

            const payload = cleanPayload(updatedData);

            console.log("JSON enviado:", payload);

            const response = await fetch(
                `${API_URL}/${currentConsolidation.work_consolidation_id}`,
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

            // 🔥 IMPORTANTE: recargar desde backend para evitar datos inconsistentes
            await loadConsolidationDetail();

            modal.style.display = "none";

        } catch (error) {
            console.error("Error updating consolidation:", error);
            alert("Error saving changes");
        }
    });

    modal.addEventListener("click", (e) => {
        if (e.target === modal) {
            modal.style.display = "none";
        }
    });

    document.addEventListener("keydown", (e) => {
        if (e.key === "Escape") {
            modal.style.display = "none";
        }
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