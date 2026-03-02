// js/config.js
export const API_BASE_URL = "http://localhost:8080/api";

export async function fetchJson(endpoint) {
    const response = await fetch(`${API_BASE_URL}${endpoint}`);
    if (!response.ok) {
        throw new Error(`Error fetching ${endpoint}`);
    }
    return response.json();
}