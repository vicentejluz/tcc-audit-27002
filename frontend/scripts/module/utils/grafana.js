import { fetchEmployee } from "../api.js";
import { handleToken } from "./token.js";

async function resultQuestion(idCompany, resultButton) {
  const orgId = 2;
  const grafanaURL =
    "http://localhost:3000/d/f29eb8e9-aa99-46b7-ba2c-7c8bdbf35eb1/aplicacao-tcc";
  resultButton.addEventListener("click", () => {
    const urlCompleta = `${grafanaURL}?var-company=${idCompany}&orgId=${orgId}&kiosk`;
    window.open(urlCompleta, "_blank");
  });
}

async function resultDashboard(employee, resultButton) {
  const orgId = 2;
  const companyId = employee.company.idCompany;
  const grafanaURL =
    "http://localhost:3000/d/f29eb8e9-aa99-46b7-ba2c-7c8bdbf35eb1/aplicacao-tcc";
  const idEmpresa = companyId;
  resultButton.addEventListener("click", () => {
    const urlCompleta = `${grafanaURL}?var-company=${idEmpresa}&orgId=${orgId}&kiosk`;
    window.open(urlCompleta, "_blank");
  });
}

async function updateIframeVarCompany(idCompany) {
  const myIframeOne = document.getElementById("myIframeOne");
  const myIframeTwo = document.getElementById("myIframeTwo");
  const myIframeThree = document.getElementById("myIframeThree");
  const orgId = 2;

  myIframeOne.src = `http://localhost:3000/d-solo/f29eb8e9-aa99-46b7-ba2c-7c8bdbf35eb1/aplicacao-tcc?var-company=${idCompany}&orgId=${orgId}&to=1685512870520&panelId=2`;

  myIframeTwo.src = `http://localhost:3000/d-solo/f29eb8e9-aa99-46b7-ba2c-7c8bdbf35eb1/aplicacao-tcc?var-company=${idCompany}&orgId=${orgId}&to=1685513865495&panelId=7`;

  myIframeThree.src = `http://localhost:3000/d-solo/f29eb8e9-aa99-46b7-ba2c-7c8bdbf35eb1/aplicacao-tcc?var-company=${idCompany}&orgId=${orgId}&to=1685513882747&panelId=1`;
}

export { resultQuestion, resultDashboard, updateIframeVarCompany };
