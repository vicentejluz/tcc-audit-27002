function roleAdmin(token) {
  const role = JSON.parse(atob(token.split(".")[1])).role;
  if (role != "ROLE_ADMIN") {
    window.location.href = "../pages/dashboard.html";
    document.body.style.display = "none";
  } else {
    document.body.style.display = "block";
  }
}

export default roleAdmin;
