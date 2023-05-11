import { fetchWithInterceptor } from "./module/utils/interceptor.js";
import {
  expirationTime,
  tokenNotExists,
  logout,
} from "./module/utils/token.js";

const token = localStorage.getItem("token");

sessionStorage.removeItem("option");

const logoutButton = document.getElementById("logout-btn");

logoutButton.addEventListener("click", () => {
  logout();
});

async function fetchEmployee() {
  const token = localStorage.getItem("token");
  const employeeId = JSON.parse(atob(token.split(".")[1])).id;
  const url = `http://localhost:8080/employee/${employeeId}`; // adiciona o ID do funcionário à URL
  try {
    const response = await fetchWithInterceptor(url, { method: "GET" });
    const data = await response.json();
    console.log(data);
    return data;
  } catch (error) {
    console.error(`Error fetching employee: ${error.message}`);
  }
}

async function init() {
  const nomeEmpresaElement = document.getElementById("nome-empresa");
  const nomePessoaElement = document.getElementById("nome-pessoa");
  tokenNotExists(token);
  expirationTime(token);
  try {
    const employee = await fetchEmployee();
    nomeEmpresaElement.textContent = employee.company.name;
    nomePessoaElement.textContent = `Bem-vindo(a), ${employee.name}`;
    document.body.style.display = "block";
  } catch (error) {
    console.error(`Error fetching employee: ${error.message}`);
    localStorage.removeItem("token");
    window.location.href = "../index.html";
  }
}

init();
