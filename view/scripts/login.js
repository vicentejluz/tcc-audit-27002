import togglePassword from "./module/utils/toggle_password.js";
import { tokenNotFound } from "./module/utils/token.js";
import { login } from "./module/api.js";

const token = localStorage.getItem("token");

const loginForm = document.querySelector("#login-form");

let eye_password = document.querySelector("#eye-password");
let password_type = document.querySelector("#password");
eye_password.addEventListener("click", function () {
  togglePassword(password_type, eye_password);
});

loginForm.addEventListener("submit", async (event) => {
  event.preventDefault(); // Previne o comportamento padrão do formulário

  const formData = new FormData(loginForm);
  const email = formData.get("email");
  const password = formData.get("password");

  await login(email, password, loginForm);
});

async function init() {
  tokenNotFound(token);
}

init();
