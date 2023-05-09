import togglePassword from "./module/utils/toggle_password.js";
import { tokenNotFound } from "./module/utils/token.js";
import { searchCep } from "./module/api.js";
import { cepInput, cnpjInput } from "./module/utils/mask.js";

const form = document.querySelector("#sign-up-form");
const streetInput = document.querySelector("#street");
const cityInput = document.querySelector("#city");
const stateSelect = document.querySelector("#state");
const option = document.querySelector('option[value=""]');
const error = document.querySelector("#error");
let eye_password = document.querySelector("#eye-password");
let password_type = document.querySelector("#password");
eye_password.addEventListener("click", function () {
  togglePassword(password_type, eye_password);
});

const token = localStorage.getItem("token");

// Add event listeners
cepInput.addEventListener("input", handleCepInput);

async function handleCepInput(event) {
  const cepValue = event.target.value.replace(/\D/g, "");
  if (cepValue.length === 8) {
    searchCep(cepValue, streetInput, cityInput, option)
      .then(handleCepResult)
      .catch(handleCepError);
    error.textContent = "";
  } else {
    clearForm();
  }
}

// Clear form values
function clearForm() {
  streetInput.value = "";
  cityInput.value = "";
  stateSelect.value = "";
  option.textContent = "Preencha o CEP";
}

// Handle CEP result
function handleCepResult(data) {
  streetInput.value = data.logradouro;
  cityInput.value = data.localidade;
  stateSelect.value = data.uf;
}

// Handle CEP error
function handleCepError(data) {
  error.textContent = data.message;
  clearForm();
}

form.addEventListener("submit", async (e) => {
  e.preventDefault(); // Impede que o formulário seja enviado automaticamente

  const formData = new FormData(form); // Obtém os dados do formulário

  const name = formData.get("name");
  const street = document.querySelector("#street").value;
  const city = document.querySelector("#city").value;
  const state = document.querySelector("#state").value;
  const postalCode = formData.get("postalCode");
  const email = formData.get("email");
  const password = formData.get("password");

  // Envia uma solicitação POST para o endpoint com os dados da empresa como corpo da solicitação
  const response = await fetch("http://localhost:8080/sign-up-company", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      address: {
        street: street,
        city: city,
        state: state,
        postalCode: postalCode,
      },
      companyDTO: {
        name: name,
        cnpj: cnpjInput.value,
      },
      loginDTO: {
        email: email,
        password: password,
      },
    }),
  });
  if (response.ok) {
    error.textContent = "";
    const responseJson = await response.json();
    const token = responseJson.token;
    localStorage.setItem("token", token);
    window.location.href = "../pages/dashboard.html";
  } else {
    const responseJson = await response.json();
    const errorCatch = responseJson;
    error.textContent = errorCatch.message;
  }
});

async function init() {
  tokenNotFound(token);
}
init();
