import { fetchEmployees, updateEmployeeStatus } from "../api.js";

let currentPage = 1;
const itemsPerPage = 5;
let employeeData;
let filteredEmployeeData;

const popupContainer = document.getElementById("popup-container");
const prevPageButton = document.getElementById("prevPage");
const nextPageButton = document.getElementById("nextPage");
const firstPageButton = document.getElementById("firstPage");
const lastPageButton = document.getElementById("lastPage");
const searchInput = document.getElementById("search-employee");

async function blockUnblockPopup() {
  popupContainer.style.display = "block";

  searchInput.value = "";

  // Carregar dados dos funcionários
  employeeData = await fetchEmployees();
  filteredEmployeeData = employeeData.filter(
    (employee) => employee.employeeRole === "EMPLOYEE"
  );

  updatePaginationButtons();

  // Popular a tabela com os dados dos funcionários
  populateEmployeeTable();
}

function createTableCell(content) {
  const cell = document.createElement("td");
  cell.textContent = content;
  return cell;
}

function createTableRow(employee, index) {
  const row = document.createElement("tr");
  row.appendChild(createTableCell(index + 1));
  row.appendChild(createTableCell(employee.name));
  row.appendChild(createTableCell(employee.email));
  row.appendChild(createTableCell(employee.department));
  row.appendChild(createTableCell(employee.isEnabled ? "Ativo" : "Bloqueado"));
  const actionButton = document.createElement("img");
  actionButton.src = employee.isEnabled
    ? "../icons/icons8-lock-32.png"
    : "../icons/icons8-unlock-32.png";
  actionButton.title = employee.isEnabled ? "Bloquear" : "Desbloquear";
  actionButton.addEventListener("click", async () => {
    await updateEmployeeStatus(employee);
    updateTableRow(row, employee);
  });
  const actionCell = document.createElement("td");
  actionCell.appendChild(actionButton);
  row.appendChild(actionCell);
  return row;
}

function populateEmployeeTable() {
  const closeButton = document.getElementById("popup-close");
  const employeeList = document.querySelector("#employee-list tbody");
  updatePaginationInfo();

  // Calcule os índices de início e fim para a paginação
  const startIndex = (currentPage - 1) * itemsPerPage;
  const endIndex = startIndex + itemsPerPage;

  employeeList.innerHTML = "";

  if (employeeData.length === 1) {
    const row = document.createElement("tr");
    const cell = document.createElement("td");
    cell.colSpan = 6; // Mescla as células para ocupar toda a largura da tabela
    cell.textContent = "Não há funcionários cadastrados";
    row.appendChild(cell);
    employeeList.appendChild(row);
  } else {
    // Use slice para mostrar apenas os itens da página atual
    const employeesToShow = filteredEmployeeData.slice(startIndex, endIndex);

    employeesToShow.forEach((employee, index) => {
      const row = createTableRow(employee, startIndex + index);
      employeeList.appendChild(row);
    });
  }

  popupContainer.addEventListener("click", function (event) {
    if (event.target === popupContainer) {
      popupContainer.style.display = "none";
    }
  });

  closeButton.addEventListener("click", () => {
    popupContainer.style.display = "none";
  });
}

searchInput.addEventListener("input", () => {
  if (employeeData.length === 1 || employeeData.length === 1) return;
  currentPage = 1;
  applySearch();
  updatePaginationButtons();
  updatePaginationInfo();
});

function applySearch() {
  const searchValue = searchInput.value.trim().toLowerCase();
  filteredEmployeeData = employeeData.filter(
    (employee) =>
      employee.employeeRole === "EMPLOYEE" &&
      employee.email.toLowerCase().includes(searchValue)
  );
  populateEmployeeTable();
}

prevPageButton.addEventListener("click", () => {
  if (currentPage > 1) {
    currentPage--;
    populateEmployeeTable(popupContainer);
  }
  updatePaginationButtons();
});

nextPageButton.addEventListener("click", () => {
  const totalEmployees = employeeData.filter(
    (employee) => employee.employeeRole === "EMPLOYEE"
  ).length;

  const maxPages = Math.ceil(totalEmployees / itemsPerPage);

  if (currentPage < maxPages) {
    currentPage++;
    populateEmployeeTable(popupContainer);
  }
  updatePaginationButtons();
});

firstPageButton.addEventListener("click", () => {
  currentPage = 1;
  populateEmployeeTable(popupContainer);
  updatePaginationButtons();
});

lastPageButton.addEventListener("click", () => {
  const totalEmployees = employeeData.filter(
    (employee) => employee.employeeRole === "EMPLOYEE"
  ).length;

  const maxPages = Math.ceil(totalEmployees / itemsPerPage);
  currentPage = maxPages;
  populateEmployeeTable(popupContainer);
  updatePaginationButtons();
});

function updatePaginationButtons() {
  const prevPageButton = document.getElementById("prevPage");
  const nextPageButton = document.getElementById("nextPage");
  const firstPageButton = document.getElementById("firstPage");
  const lastPageButton = document.getElementById("lastPage");

  const totalEmployees = filteredEmployeeData.length;
  const maxPages = Math.ceil(totalEmployees / itemsPerPage);

  if (currentPage === 1 || totalEmployees === 0) {
    prevPageButton.setAttribute("disabled", "disabled");
    firstPageButton.setAttribute("disabled", "disabled");
  } else {
    prevPageButton.removeAttribute("disabled");
    firstPageButton.removeAttribute("disabled");
  }

  if (currentPage === maxPages || totalEmployees === 0) {
    nextPageButton.setAttribute("disabled", "disabled");
    lastPageButton.setAttribute("disabled", "disabled");
  } else {
    nextPageButton.removeAttribute("disabled");
    lastPageButton.removeAttribute("disabled");
  }
}

function updatePaginationInfo() {
  const paginationInfo = document.getElementById("pagination-info");

  const totalEmployees = filteredEmployeeData.length;
  const maxPages = Math.ceil(totalEmployees / itemsPerPage);

  if (totalEmployees === 0) {
    paginationInfo.textContent = `0/0`;
  } else {
    paginationInfo.textContent = `${currentPage}/${maxPages}`;
  }
}

function updateTableRow(row, employee) {
  const statusCell = row.querySelector("td:nth-child(5)");
  const actionButton = row.querySelector("img");
  statusCell.textContent = employee.isEnabled ? "Ativo" : "Bloqueado";
  actionButton.src = employee.isEnabled
    ? "../icons/icons8-lock-32.png"
    : "../icons/icons8-unlock-32.png";
  actionButton.title = employee.isEnabled ? "Bloquear" : "Desbloquear";
}

export { blockUnblockPopup };
