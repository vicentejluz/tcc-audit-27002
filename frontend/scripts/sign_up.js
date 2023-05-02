import { fetchWithInterceptor } from "./module/utils/interceptor.js";
import togglePassword from "./module/utils/toggle_password.js";
import { expirationTime, tokenNotExists } from "./module/utils/token.js";
import roleAdmin from "./module/utils/role_admin.js";

const form = document.querySelector("#sign-up-form");
const select = document.querySelector("#department");
const error = document.querySelector("#error");

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
      error.textContent = "";
    }
  });

  confirm_password_type.addEventListener("input", () => {
    if (password_type.value === confirm_password_type.value) {
      error.textContent = "";
    }
  });

  const formData = new FormData(form); // Obtém os dados do formulário
  const name = formData.get("name");
  const email = formData.get("email");
  const password = formData.get("password");
  const confirm_password = formData.get("confirm-password");
  if (password != confirm_password) {
    error.textContent = "As senhas não coincidem!";
  } else {
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
    const employeeUrl = "http://localhost:8080/employees";
    const employeesResponse = await fetchWithInterceptor(employeeUrl, {
      method: "GET",
    });
    if (employeesResponse.ok) {
      const employees = await employeesResponse.json();
      const existingEmployee = employees.find(
        (employee) => employee.email === email
      );
      if (existingEmployee) {
        // Mostra uma mensagem de erro ao usuário
        error.textContent = "Este e-mail já existe!";
        return;
      }
    } else {
      // Mostra uma mensagem de erro ao usuário
      alert(
        "Ocorreu um erro ao verificar se este e-mail já está em uso. Tente novamente mais tarde."
      );
      return;
    }
    if (response.ok) {
      // Mostra uma mensagem de sucesso ao usuário
      alert("Cadastro realizado com sucesso!");
      // Limpa o formulário
      form.reset();
      // Volta os inputs password e confirm_password para type="password"
      password_type.type = "password";
      confirm_password_type.type = "password";
      eye_password.src = "../icons/eye-outline.svg";
      eye_confirm_senha.src = "../icons/eye-outline.svg";
    } else {
      // Mostra uma mensagem de erro ao usuário
      alert(
        "Ocorreu um erro ao realizar o cadastro. Tente novamente mais tarde."
      );
    }
  }
});

async function fetchEmployee() {
  const token = localStorage.getItem("token");
  const employeeId = JSON.parse(atob(token.split(".")[1])).id;
  const url = `http://localhost:8080/employee/${employeeId}`; // adiciona o ID do funcionário à URL
  try {
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

async function init() {
  const token = localStorage.getItem("token");
  tokenNotExists(token);
  expirationTime(token);
  await fetchEmployee();
  roleAdmin(token);
}

init();
