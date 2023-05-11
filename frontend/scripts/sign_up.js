import { fetchWithInterceptor } from "./module/utils/interceptor.js";
import { fetchEmployee } from "./module/api.js";
import togglePassword from "./module/utils/toggle_password.js";
import {
  dropDown,
  clickDropDown,
  hideDropDown,
} from "./module/utils/drop_down.js";
import { expirationTime, tokenNotExists } from "./module/utils/token.js";
import roleAdmin from "./module/utils/role_admin.js";

const token = localStorage.getItem("token");

const form = document.querySelector("#sign-up-form");
const select = document.querySelector("#department");
const msg = document.querySelector("#msg");
const inputName = document.querySelector("#name");
const inputEmail = document.querySelector("#email");
const h1Company = document.querySelector("#company");
const employeeDropdown = document.querySelector(".header-dropdown ul");
const employeeName = document.getElementById("employee-name");

const employee = await fetchEmployee(token);
h1Company.textContent = employee.company.name;
employeeName.textContent = employee.name;

dropDown(employeeDropdown);

clickDropDown(employeeName, employeeDropdown);

hideDropDown(employeeDropdown);
// Adiciona opção vazia ao menu suspenso
const defaultOption = document.createElement("option");
defaultOption.value = "";
defaultOption.text = "-- Selecione um departamento --";
select.appendChild(defaultOption);

// Realizar solicitação GET para o endpoint "http://localhost:8080/departments"
fetch("http://localhost:8080/departments")
  .then((response) => response.json()) // Converte a resposta em JSON
  .then((data) => {
    // Adiciona as opções do menu suspenso
    data.forEach((department) => {
      if (department.idDepartment != 1) {
        const option = document.createElement("option");
        option.value = department.idDepartment;
        option.text = department.name;
        select.appendChild(option);
      }
    });
  });

let eye_password = document.querySelector("#eye-password");
let password_type = document.querySelector("#password");
eye_password.addEventListener("click", function () {
  togglePassword(password_type, eye_password);
});

let eye_confirm_senha = document.querySelector("#eye-confirm-password");
let confirm_password_type = document.querySelector("#confirm-password");
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
    msg.textContent = "As senhas não coincidem!";
  } else {
    password_type.classList.remove("invalid-input");
    confirm_password_type.classList.remove("invalid-input");
    // Envia uma solicitação POST para o endpoint com os dados da empresa como corpo da solicitação
    const url = "http://localhost:8080/sign-up";
    const response = await fetchWithInterceptor(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name: name,
        loginDTO: {
          email: email,
          password: password,
        },
        idDepartment: select.value,
      }),
    });
    if (response.ok) {
      msg.classList.add("success");
      msg.textContent = "Cadastrado com sucesso!";
      inputName.classList.remove("invalid-input");
      inputEmail.classList.remove("invalid-input");
      password_type.type = "password";
      confirm_password_type.type = "password";
      eye_password.src = "../icons/eye-outline.svg";
      eye_confirm_senha.src = "../icons/eye-outline.svg";

      // Remove a mensagem de sucesso após 3 segundos
      setTimeout(() => {
        msg.textContent = "";
        msg.classList.remove("success");
      }, 2000);
      form.reset();
    } else {
      // Mostra uma mensagem de erro ao usuário
      const responseJson = await response.json();
      const errorCatch = responseJson;
      // Quando ocorre um erro, adiciona a classe 'invalid-input' ao input
      if (errorCatch.error === "name error") {
        inputName.classList.add("invalid-input");
      } else {
        inputName.classList.remove("invalid-input");
      }
      if (errorCatch.error === "email error") {
        inputEmail.classList.add("invalid-input");
      } else {
        inputEmail.classList.remove("invalid-input");
      }
      msg.textContent = errorCatch.message;
    }
  }
});

async function init() {
  await fetchEmployee(token);
  tokenNotExists(token);
  expirationTime(token);
  roleAdmin(token);
}

init();
