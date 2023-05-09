function tokenNotExists(token) {
  if (!token) {
    window.location.href = "../index.html";
    document.body.style.display = "none";
  }
}

function tokenNotFound(token) {
  if (token) {
    window.location.href = "../pages/dashboard.html";
  } else {
    document.body.style.display = "block";
  }
}

function expirationTime(token) {
  const decodedToken = JSON.parse(atob(token.split(".")[1]));
  const currentTime = Math.floor(Date.now() / 1000);
  if (currentTime > decodedToken.exp) {
    // Se a data de expiração passou, redireciona para a tela de login
    localStorage.removeItem("token");
    window.location.href = "../index.html";
    document.body.style.display = "none";
  }
}

function logout() {
  localStorage.removeItem("token");
  window.location.href = "../index.html";
}

export { expirationTime, tokenNotExists, logout, tokenNotFound };
