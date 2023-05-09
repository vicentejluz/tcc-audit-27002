import { fetchWithInterceptor } from "./utils/interceptor.js";

async function fetchEmployee(token) {
  try {
    const employeeId = JSON.parse(atob(token.split(".")[1])).id;
    const url = `http://localhost:8080/employee/${employeeId}`;
    const response = await fetchWithInterceptor(url, { method: "GET" });
    if (response.ok) {
      const data = await response.json();
      return data;
    } else {
      console.error(`Error fetching employee: ${response.status}`);
      throw new Error("Failed to fetch employee");
    }
  } catch (error) {
    console.error(`Error fetching employee: ${error.message}`);
    localStorage.removeItem("token");
    window.location.href = "../index.html";
  }
}

// Search CEP using ViaCEP API
async function searchCep(cep, streetInput, cityInput, option) {
  streetInput.value = "Buscando...";
  cityInput.value = "Buscando...";
  option.textContent = "Buscando...";

  const apiUrl = `http://localhost:8080/via-cep?postalCode=${cep}`;
  const response = await fetch(apiUrl);
  if (response.ok) {
    return response.json();
  } else {
    const responseJson = await response.json();
    const errorCatch = responseJson;
    throw new Error(errorCatch.message);
  }
}

export { fetchEmployee, searchCep };
