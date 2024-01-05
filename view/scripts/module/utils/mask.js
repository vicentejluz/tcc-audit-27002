const cepInput = document.querySelector("#postalCode");
const cnpjInput = document.querySelector("#cnpj");

// Validate CEP Input
cepInput.addEventListener("keypress", (e) => {
  onlyNumber(e);
});

// Evento to get address
cepInput.addEventListener("keyup", (e) => {
  let inputValue = e.target;
  inputValue.value = zipCodeMask(inputValue.value);
});

const zipCodeMask = (value) => {
  if (!value) return "";
  value = value.replace(/\D/g, "");
  value = value.replace(/(\d{5})(\d)/, "$1-$2");
  return value;
};

// Validate CNPJ Input
cnpjInput.addEventListener("keypress", (e) => {
  onlyNumber(e);
});

cnpjInput.addEventListener("keyup", (e) => {
  let inputValue = e.target;
  inputValue.value = cnpjMask(inputValue.value);
});

const cnpjMask = (value) => {
  if (!value) return "";
  value = value.replace(/\D/g, "");
  value = value.replace(/^(\d{2})(\d)/, "$1.$2");
  value = value.replace(/^(\d{2})\.(\d{3})(\d)/, "$1.$2.$3");
  value = value.replace(/\.(\d{3})(\d)/, ".$1/$2");
  value = value.replace(/(\d{4})(\d)/, "$1-$2");
  return value;
};

// Validate CPF Input
function onlyNumber(e) {
  const onlyNumbers = /^[0-9]+$/;
  const key = String.fromCharCode(e.keyCode);

  // allow only numbers
  if (!onlyNumbers.test(key)) {
    e.preventDefault();
    return;
  }
}

export { cepInput, cnpjInput };
