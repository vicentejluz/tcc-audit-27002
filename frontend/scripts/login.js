import togglePassword from "./module/utils/toggle_password.js";
import { tokenNotFound } from "./module/utils/token.js";

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

  const response = await fetch("http://localhost:8080/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      email: email,
      password: password,
    }),
  });
  const error = document.querySelector("#error");
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
    loginForm.reset();
  }
});

async function init() {
  tokenNotFound(token);
}
init();
