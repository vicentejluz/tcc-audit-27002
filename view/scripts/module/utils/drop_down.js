import { logout } from "./token.js";
import { blockUnblockPopup } from "./block_unblock_popup.js";

// Define as opções do dropdown
const dropdownOptions = [
  { label: "Bloquear/Desbloquear", id: "block-unblock" },
  { label: "Painel", link: "../pages/dashboard.html" },
  { label: "Sair", link: "../index.html" },
];

function dropDown(employeeDropdown) {
  // Popula o dropdown com as opções
  dropdownOptions.forEach((option) => {
    const li = document.createElement("li");
    const a = document.createElement("a");
    a.href = option.link || "#";
    a.innerText = option.label;
    if (option.id === "block-unblock") {
      a.addEventListener("click", () => {
        blockUnblockPopup();
      });
    } else if (option.label === "Sair") {
      a.addEventListener("click", logout);
    }
    li.appendChild(a);
    employeeDropdown.appendChild(li);
  });
}

function clickDropDown(employeeName, employeeDropdown) {
  // Mostra o dropdown quando o span é clicado
  employeeName.addEventListener("click", () => {
    employeeDropdown.classList.toggle("show");
  });
}

function hideDropDown(employeeDropdown) {
  // Esconde o dropdown quando se clica fora dele
  window.addEventListener("click", (event) => {
    if (!event.target.matches(".header-dropdown span")) {
      if (employeeDropdown.classList.contains("show")) {
        employeeDropdown.classList.remove("show");
      }
    }
  });
}

export { dropDown, clickDropDown, hideDropDown };
