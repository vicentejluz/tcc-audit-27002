function roleAdmin(token) {
  const role = JSON.parse(atob(token.split(".")[1])).employeeRole;
  if (role != "ADMIN") {
    window.location.href = "../pages/dashboard.html";
    document.body.style.display = "none";
  } else {
    document.body.style.display = "block";
  }
}

function isAdmin(token) {
  const buttonEmployeeRegistration = document.getElementById(
    "button-employee-registration"
  );
  const role = JSON.parse(atob(token.split(".")[1])).employeeRole;
  if (role != "ADMIN") {
    buttonEmployeeRegistration.remove();
  }
}

export { roleAdmin, isAdmin };
