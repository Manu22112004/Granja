import { jsPDF } from "https://cdn.jsdelivr.net/npm/jspdf@2.5.1/+esm";

export function convertReportToPDF(
    tableElement,
    companyElement,
    dateElement,
    pullElement,
    customerElement,
    farmsElement,
    bedsElement,
    leaderElement,
    checkerElement
) {

    const doc = new jsPDF({
        orientation: "portrait",
        unit: "mm",
        format: "a4"
    });

    const table = document.getElementById(tableElement);

    const company = document.getElementById(companyElement).textContent;
    const date = document.getElementById(dateElement).textContent;
    const pull = document.getElementById(pullElement).textContent;
    const customer = document.getElementById(customerElement).textContent;
    const farms = document.getElementById(farmsElement).textContent;
    const beds = document.getElementById(bedsElement).textContent;
    const leader = document.getElementById(leaderElement).textContent;
    const checker = document.getElementById(checkerElement).textContent;

    const centerX = 105; // centro A4

    // HEADER
    doc.setFont("helvetica", "bold");
    doc.setFontSize(18);
    doc.text(company, centerX, 18, { align: "center" });

    doc.setFontSize(14);
    doc.text("Production Report", centerX, 26, { align: "center" });

    // Línea separadora
    doc.setLineWidth(0.5);
    doc.line(15, 30, 195, 30);

    // INFO SECTION
    doc.setFont("helvetica", "normal");
    doc.setFontSize(11);

    let y = 38;

    doc.text(`Date: ${date}`, 15, y);
    doc.text(`Pull: ${pull}`, 110, y);

    y += 8;

    doc.text(`Customer: ${customer}`, 15, y);
    doc.text(`# Farms: ${farms}`, 110, y);

    y += 8;

    doc.text(`# Beds: ${beds}`, 15, y);
    doc.text(`Crew Leader: ${leader}`, 110, y);

    y += 8;

    doc.text(`Quality Checker: ${checker}`, 15, y);

    // Línea separadora antes de tabla
    y += 6;
    doc.line(15, y, 195, y);

    // TABLA
    doc.html(table, {
        callback: function (pdf) {

            const pageCount = pdf.getNumberOfPages();

            for (let i = 1; i <= pageCount; i++) {
                pdf.setPage(i);

                pdf.setFontSize(9);
                pdf.text(
                    `Page ${i} of ${pageCount}`,
                    105,
                    290,
                    { align: "center" }
                );
            }

            pdf.save("production-report.pdf");
        },
        x: 15,
        y: y + 8,
        html2canvas: {
            scale: 0.28
        }
    });
}