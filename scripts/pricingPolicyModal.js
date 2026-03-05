import { API_BASE_URL } from "./config.js";

/*=============
    INIT VAR
=============*/
const PRICING_POLICIES_URL = `${API_BASE_URL}/pricing-policies`;

/*==============
    LOAD PAGE
==============*/
document.addEventListener("DOMContentLoaded", () => {

    //DEFINE FUNCTIONS FOR BUTTONS
    const pricingBtn = document.getElementById("pricingBtn");
    const modal = document.getElementById("pricingModal");
    const closeBtn = document.getElementById("closePricingModal");
    const saveBtn = document.getElementById("savePricingBtn");

    //INIT VAR TO HOLD CURRENT PRICING POLICY
    let currentPricingPolicy = null;

    //OPEN MODAL AND LOAD CURRENT PRICING POLICY
    pricingBtn.addEventListener("click", async () => {
        if (!window.currentConsolidation) return;

        const pricingPolicyId = window.currentConsolidation.pricing_policy_id;
        if (!pricingPolicyId) return;

        const res = await fetch(`${PRICING_POLICIES_URL}/${pricingPolicyId}`);
        if (!res.ok) return;

        const raw = await res.json();

        currentPricingPolicy = {
            pricingPolicyId: raw.pricing_policy_id,
            pricePerHour: raw.price_per_hour,
            pricePerBed: raw.price_per_bed,
            effectiveFrom: raw.effective_from,
            active: raw.active,
            workConsolidationId: raw.work_consolidation_id
        };

        console.log("PricingPolicy normalizada:", currentPricingPolicy);

        document.getElementById("pricing_price_per_bed").value =
            currentPricingPolicy.pricePerBed ?? 0;

        document.getElementById("pricing_price_per_hour").value =
            currentPricingPolicy.pricePerHour ?? 0;

        modal.style.display = "flex";
    });

    //CLOSE MODAL
    closeBtn.addEventListener("click", () => {
        modal.style.display = "none";
    });

    //SAVE PRICING POLICY
    saveBtn.addEventListener("click", async () => {
        if (!currentPricingPolicy) {
            alert("Pricing policy not loaded");
            return;
        }

        try {
            const payload = {
                pricePerBed: Number(
                    document.getElementById("pricing_price_per_bed").value
                ),
                pricePerHour: Number(
                    document.getElementById("pricing_price_per_hour").value
                ),

                effectiveFrom: currentPricingPolicy.effectiveFrom,
                active: currentPricingPolicy.active,

                workConsolidationId:
                    window.currentConsolidation.work_consolidation_id
            };

            console.log("PricingPolicyId:", currentPricingPolicy.pricingPolicyId);

            const response = await fetch(
                `${PRICING_POLICIES_URL}/${currentPricingPolicy.pricingPolicyId}`,
                {
                    method: "PUT",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(payload)
                }
            );

            if (!response.ok) {
                const errorText = await response.text();
                console.error("Pricing backend error:", errorText);
                alert("Error saving pricing");
                return;
            }

            modal.style.display = "none";
            await window.loadConsolidationDetail();

        } catch (error) {
            console.error("Pricing error:", error);
            alert("Unexpected error saving pricing");
        }
    });

});