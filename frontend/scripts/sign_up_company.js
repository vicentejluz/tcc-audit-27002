import togglePassword from "./module/utils/toggle_password.js";
import { tokenNotFound } from "./module/utils/token.js";

let eye_password = document.querySelector("#eye-password");
let password_type = document.querySelector("#password");
eye_password.addEventListener("click", function () {
  togglePassword(password_type, eye_password);
});

const form = document.querySelector("#sign-up-form");

form.addEventListener("submit", async (e) => {
  e.preventDefault(); // Impede que o formulário seja enviado automaticamente

  const formData = new FormData(form); // Obtém os dados do formulário

  const name = formData.get("name");
  const cnpj = formData.get("cnpj");
  const street = formData.get("street");
  const city = formData.get("city");
  const state = formData.get("state");
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
        cnpj: cnpj,
      },
      loginDTO: {
        email: email,
        password: password,
      },
    }),
  });
  if (response.ok) {
    const responseJson = await response.json();
    const token = responseJson.token;
    localStorage.setItem("token", token);
    window.location.href = "../pages/dashboard.html";
  } else {
    const responseJson = await response.json();
    const error = responseJson;
    alert(error);
  }
});

async function init() {
  tokenNotFound();
}
init();
