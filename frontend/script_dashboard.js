const logoutButton = document.getElementById("logout-btn");

logoutButton.addEventListener("click", () => {
  localStorage.removeItem("token");
  window.location.href = "login.html";
});

async function fetchEmployee() {
  const token = localStorage.getItem("token");
  const employeeId = JSON.parse(atob(token.split(".")[1])).id;
  console.log(employeeId); // decodifica o token e pega o ID do funcionário
  const url = `http://localhost:8080/employee/${employeeId}`; // adiciona o ID do funcionário à URL
  try {
    const response = await fetchWithInterceptor(url, { method: "GET" });
    const data = await response.json();
    console.log(data);
    return data;
  } catch (error) {
    console.error(`Error fetching employee: ${error.message}`);
  }
}

async function init() {
  const nomeEmpresaElement = document.getElementById("nome-empresa");
  const nomePessoaElement = document.getElementById("nome-pessoa");

  const token = localStorage.getItem("token");
  if (!token) {
    window.location.href = "login.html";
    document.body.style.display = "none";
    return;
  }

  const decodedToken = JSON.parse(atob(token.split(".")[1]));
  const currentTime = Math.floor(Date.now() / 1000);
  if (currentTime > decodedToken.exp) {
    // Se a data de expiração passou, redireciona para a tela de login
    localStorage.removeItem("token");
    window.location.href = "login.html";
    document.body.style.display = "none";
    return;
  }
  try {
    const employee = await fetchEmployee();
    console.log(employee);
    console.log(employee.company.name);
    console.log(employee.name);
    nomeEmpresaElement.textContent = employee.company.name;
    nomePessoaElement.textContent = `Bem-vindo(a), ${employee.name}`;
    document.body.style.display = "block";
  } catch (error) {
    console.error(`Error fetching employee: ${error.message}`);
    localStorage.removeItem("token");
    window.location.href = "login.html";
  }
}

const interceptor = {
  async request(url, options) {
    const token = localStorage.getItem("token");
    console.log(token);
    if (token) {
      if (!options.headers) {
        options.headers = {};
      }
      options.headers.Authorization = `Bearer ${token}`;
    }
    return [url, options];
  },
  response(response) {
    return response;
  },
};

async function fetchWithInterceptor(url, options) {
  const [newUrl, newOptions] = await interceptor.request(url, options);
  const response = await fetch(newUrl, newOptions);
  return interceptor.response(response);
}

init();
