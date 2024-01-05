import { fetchEmployee } from "./module/api.js";
import {
  expirationTime,
  tokenNotExists,
  logout,
} from "./module/utils/token.js";
import { resultDashboard } from "./module/utils/grafana.js";
import { isAdmin } from "./module/utils/role_admin.js";

const token = localStorage.getItem("token");


sessionStorage.removeItem("option");
sessionStorage.removeItem("congratulation");

const nomeEmpresaElement = document.getElementById("company-name");
const nomePessoaElement = document.getElementById("person-name");
const cnpj = document.getElementById("cnpj");
const postalCode = document.getElementById("postal-code");
const street = document.getElementById("street");
const city = document.getElementById("city");
const state = document.getElementById("state");
const organizationalControls = document.querySelector(
  "#organizational-controls"
);
const controlsForPeople = document.querySelector("#controls-for-people");
const physicalControls = document.querySelector("#physical-controls");
const technologicalControls = document.querySelector("#technological-controls");

const logoutButton = document.querySelector("#logout-btn");

logoutButton.addEventListener("click", () => {
  logout();
});

organizationalControls.addEventListener("click", () => {
  const option = 5;
  sessionStorage.setItem("option", option);
  window.location.href = "../pages/question.html";
});

controlsForPeople.addEventListener("click", () => {
  const option = 6;
  sessionStorage.setItem("option", option);
  window.location.href = "../pages/question.html";
});

physicalControls.addEventListener("click", () => {
  const option = 7;
  sessionStorage.setItem("option", option);
  window.location.href = "../pages/question.html";
});

technologicalControls.addEventListener("click", () => {
  const option = 8;
  sessionStorage.setItem("option", option);
  window.location.href = "../pages/question.html";
});

const resultButton = document.querySelector("#result");

function printCompanyData(employee) {
  nomeEmpresaElement.textContent = employee.company.name;
  nomePessoaElement.innerHTML = `Bem-vindo(a), ${employee.name}<br>Escolha uma das opções abaixo para responder o questionário.`;
  cnpj.textContent = `CNPJ: ${employee.company.cnpj}`;
  postalCode.textContent = `CEP: ${employee.company.address.postalCode}`;
  street.textContent = `Rua: ${employee.company.address.street}`;
  city.textContent = `Cidade: ${employee.company.address.city}`;
  state.textContent = `Estado: ${employee.company.address.state}`;
}

async function init() {
  tokenNotExists(token);
  expirationTime(token);
  isAdmin(token);
  const employee = await fetchEmployee(token);
  printCompanyData(employee);
  resultDashboard(employee, resultButton);
}

setTimeout(() => {
  init();
}, 300);
