const loginForm = document.querySelector("#login-form");

loginForm.addEventListener("submit", async (event) => {
  event.preventDefault(); // evita que o formulário seja enviado via método POST

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
      senha: password,
    }),
  });

  if (response.ok) {
    const responseJson = await response.json();
    const message = responseJson;
    alert(message);
    window.location.href = "./perguntas.html";
  } else {
    const responseJson = await response.json();
    const error = responseJson;
    alert(error + ": Email ou senha inválido.");
  }
});
