/*================
    URL GLOBAL
================*/
//export const API_BASE_URL = "http://localhost:8080/api";
export const API_BASE_URL = "https://granja-1ryk.onrender.com/api";
//PROTOTIPE
export async function fetchJson(endpoint) {
    const response = await fetch(`${API_BASE_URL}${endpoint}`);
    if (!response.ok) {
        throw new Error(`Error fetching ${endpoint}`);
    }
    return response.json();
}