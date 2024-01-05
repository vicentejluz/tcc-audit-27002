import {
  fetchEmployee,
  getDepartments,
  registerEmployee,
} from "./module/api.js";
import togglePassword from "./module/utils/toggle_password.js";

import {
  dropDown,
  clickDropDown,
  hideDropDown,
} from "./module/utils/drop_down.js";
import {
  expirationTime,
  tokenNotExists,
  handleToken,
} from "./module/utils/token.js";
import { roleAdmin } from "./module/utils/role_admin.js";

const token = localStorage.getItem("token");
sessionStorage.removeItem("congratulation");

const form = document.querySelector("#sign-up-form");
const select = document.querySelector("#department");
const msg = document.querySelector("#msg");
const inputName = document.querySelector("#name");
const inputEmail = document.querySelector("#email");

const h1Company = document.querySelector("#company");
const employeeDropdown = document.querySelector(".header-dropdown ul");
const employeeName = document.querySelector("#employee-name");
const eye_password = document.querySelector("#eye-password");
const password_type = document.querySelector("#password");
const eye_confirm_senha = document.querySelector("#eye-confirm-password");
const confirm_password_type = document.querySelector("#confirm-password");

// Adiciona opção vazia ao menu suspenso
const defaultOption = document.createElement("option");
defaultOption.value = "";
defaultOption.text = "-- Selecione um departamento --";
select.appendChild(defaultOption);

eye_password.addEventListener("click", function () {
  togglePassword(password_type, eye_password);
});

eye_confirm_senha.addEventListener("click", function () {
  togglePassword(confirm_password_type, eye_confirm_senha);
});

form.addEventListener("submit", async (e) => {
  e.preventDefault(); // Impede que o formulário seja enviado automaticamente
  password_type.addEventListener("input", () => {
    if (password_type.value === confirm_password_type.value) {
      msg.textContent = "";
    }
  });

  confirm_password_type.addEventListener("input", () => {
    if (password_type.value === confirm_password_type.value) {
      msg.textContent = "";
    }
  });

  inputName.addEventListener("invalid", () => {
    msg.textContent = "";
  });

  select.addEventListener("invalid", () => {
    msg.textContent = "";
  });

  inputEmail.addEventListener("invalid", () => {
    msg.textContent = "";
  });

  const formData = new FormData(form); // Obtém os dados do formulário
  const name = formData.get("name");
  const email = formData.get("email");
  const password = formData.get("password");
  const confirm_password = formData.get("confirm-password");
  if (password != confirm_password) {
    password_type.classList.add("invalid-input");
    confirm_password_type.classList.add("invalid-input");
    inputName.classList.remove("invalid-input");
    inputEmail.classList.remove("invalid-input");
    msg.textContent = "As senhas não coincidem!";
  } else {
    password_type.classList.remove("invalid-input");
    confirm_password_type.classList.remove("invalid-input");
    // Envia uma solicitação POST para o endpoint com os dados da empresa como corpo da solicitação
    await registerEmployee(
      form,
      name,
      email,
      password,
      select,
      msg,
      inputName,
      inputEmail,
      password_type,
      confirm_password_type,
      eye_password,
      eye_confirm_senha
    );
  }
});

async function init() {
  tokenNotExists(token);
  expirationTime(token);
  const employee = await fetchEmployee(token);
  roleAdmin(token);
  h1Company.textContent = handleToken(token).tokenCompany;
  employeeName.textContent = handleToken(token).tokenName;
  dropDown(employeeDropdown);
  clickDropDown(employeeName, employeeDropdown, employee);
  hideDropDown(employeeDropdown);
  getDepartments(select);
}
setTimeout(() => {
  init();
}, 300);
