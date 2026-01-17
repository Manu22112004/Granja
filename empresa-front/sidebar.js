/* =================
   SIDEBAR
   ================= */
const toggleButton = document.getElementById('toggleSidebar');
const appLayout = document.getElementById('appLayout');


/* TOGGLE + SAVE STATE */
toggleButton.addEventListener('click', () => {
    appLayout.classList.toggle('sidebar-collapsed');
});
