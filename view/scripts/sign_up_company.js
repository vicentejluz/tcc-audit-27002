import togglePassword from "./module/utils/toggle_password.js";
import { tokenNotFound } from "./module/utils/token.js";
import { searchCep, registerCompany } from "./module/api.js";
import { cepInput, cnpjInput } from "./module/utils/mask.js";

const form = document.querySelector("#sign-up-form");
const streetInput = document.querySelector("#street");
const cityInput = document.querySelector("#city");
const stateSelect = document.querySelector("#state");
const option = document.querySelector('option[value=""]');
const msg = document.querySelector("#msg");
const inputEmail = document.querySelector("#email");
const button = document.querySelector("#register");

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
    searchCep(cepValue, streetInput, cityInput, option, button)
      .then(handleCepResult)
      .catch(handleCepError);
    cepInput.classList.remove("invalid-input");
    msg.textContent = "";
  } else {
    clearForm();
    cepInput.classList.add("invalid-input");
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
  cepInput.classList.add("invalid-input");
  msg.textContent = data.message;
  clearForm();
}

form.addEventListener("submit", async (e) => {
  e.preventDefault(); // Impede que o formulário seja enviado automaticamente
  let selectedOption = stateSelect.options[stateSelect.selectedIndex];
  let value = selectedOption.value;
  let textContent = selectedOption.textContent;
  const formData = new FormData(form); // Obtém os dados do formulário

  const companyName = formData.get("companyName");
  const street = document.querySelector("#street").value;
  const city = document.querySelector("#city").value;

  const selectedValue = value === "" ? value : textContent;
  const postalCode = formData.get("postalCode");
  const email = formData.get("email");
  const password = formData.get("password");

  // Envia uma solicitação POST para o endpoint com os dados da empresa como corpo da solicitação
  await registerCompany(
    street,
    city,
    selectedValue,
    postalCode,
    companyName,
    cnpjInput,
    cepInput,
    inputEmail,
    email,
    password
  );
});

async function init() {
  tokenNotFound(token);
}
init();
