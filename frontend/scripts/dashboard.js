import { fetchEmployee } from "./module/api.js";
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

async function init() {
  const nomeEmpresaElement = document.getElementById("nome-empresa");
  const nomePessoaElement = document.getElementById("nome-pessoa");
  tokenNotExists(token);
  expirationTime(token);
  const employee = await fetchEmployee(token);
  nomeEmpresaElement.textContent = employee.company.name;
  nomePessoaElement.textContent = `Bem-vindo(a), ${employee.name}`;
}

setTimeout(() => {
  init();
}, 300);
