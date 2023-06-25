import { fetchEmployee } from "../api.js";

async function resultQuestion(token, resultButton) {
  const orgId = 3;
  const employee = await fetchEmployee(token);
  const companyId = employee.company.idCompany;
  const grafanaURL =
    "http://localhost:3000/d/f29eb8e9-aa99-46b7-ba2c-7c8bdbf35eb1/aplicacao-tcc";
  const idEmpresa = companyId;
  resultButton.addEventListener("click", () => {
    const urlCompleta = `${grafanaURL}?var-company=${idEmpresa}&orgId=${orgId}&kiosk`;
    window.open(urlCompleta, "_blank");
  });
}

async function resultDashboard(employee, resultButton) {
  const orgId = 3;
  const companyId = employee.company.idCompany;
  const grafanaURL =
    "http://localhost:3000/d/f29eb8e9-aa99-46b7-ba2c-7c8bdbf35eb1/aplicacao-tcc";
  const idEmpresa = companyId;
  resultButton.addEventListener("click", () => {
    const urlCompleta = `${grafanaURL}?var-company=${idEmpresa}&orgId=${orgId}&kiosk`;
    window.open(urlCompleta, "_blank");
  });
}

export { resultQuestion, resultDashboard };
