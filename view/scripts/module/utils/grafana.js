const orgId = 1;
const uid = "f29eb8e9-aa99-46b7-ba2c-7c8bdbf35eb1";
const port = 3000;
const grafanaURL = `http://localhost:${port}/d/${uid}/aplicacao-tcc`;

async function resultQuestion(idCompany, resultButton) {
  resultButton.addEventListener("click", () => {
    const urlCompleta = `${grafanaURL}?var-company=${idCompany}&orgId=${orgId}&kiosk`;
    window.open(urlCompleta, "_blank");
  });
}

async function resultDashboard(employee, resultButton) {
  const companyId = employee.company.idCompany;
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

  myIframeOne.src = `http://localhost:${port}/d-solo/${uid}/aplicacao-tcc?var-company=${idCompany}&orgId=${orgId}&to=1685512870520&panelId=2`;

  myIframeTwo.src = `http://localhost:${port}/d-solo/${uid}/aplicacao-tcc?var-company=${idCompany}&orgId=${orgId}&to=1685513865495&panelId=7`;

  myIframeThree.src = `http://localhost:${port}/d-solo/${uid}/aplicacao-tcc?var-company=${idCompany}&orgId=${orgId}&to=1685513882747&panelId=1`;
}

export { resultQuestion, resultDashboard, updateIframeVarCompany };
