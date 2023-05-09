const loginForm = document.querySelector("#login-form");

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

  if (response.ok) {
    const responseJson = await response.json();
    const token = responseJson.token;
    localStorage.setItem("token", token); // Salva o token no localStorage para usá-lo em outras solicitações
    window.location.href = "./dashboard.html";
  } else {
    const responseJson = await response.json();
    const error = responseJson;
    alert(error + ": Email ou senha inválido.");
  }
});

async function init() {
  document.body.style.display = "none";
  if (localStorage.getItem("token")) {
    window.location.href = "./dashboard.html";
  } else {
    document.body.style.display = "block";
  }
}
init();
