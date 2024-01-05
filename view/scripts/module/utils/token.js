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

  const tokenIdCompany = JSON.parse(atob(token.split(".")[1])).idCompany;

  const base64UrlCompany = token.split(".")[1];
  const base64Company = base64UrlCompany.replace(/-/g, "+").replace(/_/g, "/");
  const decodedTokenCompany = decodeURIComponent(
    atob(base64Company)
      .split("")
      .map((c) => `%${`00${c.charCodeAt(0).toString(16)}`.slice(-2)}`)
      .join("")
  );

  const tokenCompany = JSON.parse(decodedTokenCompany).company;

  const base64UrlName = token.split(".")[1];
  const base64Name = base64UrlName.replace(/-/g, "+").replace(/_/g, "/");
  const decodedTokenName = decodeURIComponent(
    atob(base64Name)
      .split("")
      .map((c) => `%${`00${c.charCodeAt(0).toString(16)}`.slice(-2)}`)
      .join("")
  );
  const tokenName = JSON.parse(decodedTokenName).name;

  return { tokenCompany, tokenName, tokenIdCompany };
}

function logout() {
  localStorage.removeItem("token");
  window.location.href = "../index.html";
}

export { expirationTime, tokenNotExists, logout, tokenNotFound, handleToken };
