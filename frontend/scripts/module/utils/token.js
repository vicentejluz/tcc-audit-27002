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
    // Se a data de expiração passou, redireciona para a tela de index
    localStorage.removeItem("token");
    window.location.href = "../index.html";
    document.body.style.display = "none";
  }
}

function handleToken(token) {
  const tokenCompany = JSON.parse(atob(token.split(".")[1])).company;

  const base64Url = token.split(".")[1];
  const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
  const decodedToken = decodeURIComponent(
    atob(base64)
      .split("")
      .map((c) => `%${`00${c.charCodeAt(0).toString(16)}`.slice(-2)}`)
      .join("")
  );
  const tokenName = JSON.parse(decodedToken).name;

  return { tokenCompany, tokenName };
}

function logout() {
  localStorage.removeItem("token");
  window.location.href = "../index.html";
}

export { expirationTime, tokenNotExists, logout, tokenNotFound, handleToken };
