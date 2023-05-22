import { fetchWithInterceptor } from "./utils/interceptor.js";

async function fetchEmployee(token) {
  const employeeId = JSON.parse(atob(token.split(".")[1])).id;
  const apiUrl = `http://localhost:8080/employee/${employeeId}`;
  try {
    const response = await fetchWithInterceptor(apiUrl, { method: "GET" });
    if (response.ok) {
      const data = await response.json();
      document.body.style.display = "block";
      return data;
    } else {
      console.error(`Error fetching employee: ${response.status}`);
      localStorage.removeItem("token");
      window.location.href = "../index.html";
      throw new Error("Failed to fetch employee");
    }
  } catch (error) {
    console.error(`Error fetching employee: ${error.message}`);
    localStorage.removeItem("token");
    window.location.href = "../index.html";
  }
}

async function login(email, password, loginForm) {
  const error = document.querySelector("#error");
  const apiUrl = "http://localhost:8080/login";
  try {
    const response = await fetchWithInterceptor(apiUrl, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        email: email,
        password: password,
      }),
    });
    if (response.ok) {
      error.textContent = "";
      const responseJson = await response.json();
      const token = responseJson.token;
      localStorage.setItem("token", token);
      window.location.href = "../pages/dashboard.html";
    } else {
      const responseJson = await response.json();
      const errorElse = responseJson;
      error.textContent = errorElse.message;
      loginForm.reset();
    }
  } catch (errorCatch) {
    error.textContent = errorCatch.message;
    loginForm.reset();
  }
}

async function registerCompany(
  street,
  city,
  selectedValue,
  postalCode,
  name,
  cnpjInput,
  cepInput,
  inputEmail,
  email,
  password
) {
  const msg = document.querySelector("#msg");
  const apiUrl = "http://localhost:8080/sign-up-company";
  try {
    const response = await fetchWithInterceptor(apiUrl, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        address: {
          street: street,
          city: city,
          state: selectedValue,
          postalCode: postalCode,
        },
        companyDTO: {
          name: name,
          cnpj: cnpjInput.value,
        },
        loginDTO: {
          email: email,
          password: password,
        },
      }),
    });
    if (response.ok) {
      cnpjInput.classList.remove("invalid-input");
      inputEmail.classList.remove("invalid-input");
      cepInput.classList.remove("invalid-input");
      const responseJson = await response.json();
      const token = responseJson.token;
      localStorage.setItem("token", token);
      window.location.href = "../pages/dashboard.html";
    } else {
      const responseJson = await response.json();
      const error = responseJson;
      if (error.error === "cnpj error") {
        cnpjInput.classList.add("invalid-input");
      } else {
        cnpjInput.classList.remove("invalid-input");
      }
      if (error.error === "email error") {
        inputEmail.classList.add("invalid-input");
      } else {
        inputEmail.classList.remove("invalid-input");
      }
      msg.textContent = error.message;
    }
  } catch (error) {
    msg.textContent = error.message;
  }
}

// Search CEP using ViaCEP API
async function searchCep(cep, streetInput, cityInput, option, button) {
  button.disabled = true;
  streetInput.value = "Buscando...";
  cityInput.value = "Buscando...";
  option.textContent = "Buscando...";

  const apiUrl = `http://localhost:8080/via-cep?postalCode=${cep}`;
  try {
    const response = await fetchWithInterceptor(apiUrl, { method: "GET" });
    if (response.ok) {
      button.disabled = false;
      return response.json();
    } else {
      button.disabled = false;
      const responseJson = await response.json();
      const errorCatch = responseJson;
      throw new Error(errorCatch.message);
    }
  } catch (error) {
    button.disabled = false;
    throw new Error(error.message);
  }
}

async function getDepartments(select) {
  const apiUrl = "http://localhost:8080/departments";
  try {
    const response = await fetchWithInterceptor(apiUrl, { method: "GET" });
    if (response.ok) {
      const data = await response.json();
      data.forEach((department) => {
        if (department.idDepartment != 1) {
          const option = document.createElement("option");
          option.value = department.idDepartment;
          option.text = department.name;
          select.appendChild(option);
        }
      });
    } else {
      throw new Error("Erro ao obter departamentos");
    }
  } catch (error) {
    console.log(error);
  }
}

async function registerEmployee(
  form,
  name,
  email,
  password,
  select,
  msg,
  inputName,
  inputEmail,
  password_type,
  confirm_password_type,
  eye_password,
  eye_confirm_senha
) {
  const apiUrl = "http://localhost:8080/sign-up";
  try {
    const response = await fetchWithInterceptor(apiUrl, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name: name,
        loginDTO: {
          email: email,
          password: password,
        },
        idDepartment: select.value,
      }),
    });

    if (response.ok) {
      const button = document.querySelector("#btn-register");
      button.disabled = true;
      msg.classList.add("success");
      msg.textContent = "Cadastrado com sucesso!";
      inputName.classList.remove("invalid-input");
      inputEmail.classList.remove("invalid-input");
      password_type.type = "password";
      confirm_password_type.type = "password";
      eye_password.src = "../icons/eye-outline.svg";
      eye_confirm_senha.src = "../icons/eye-outline.svg";

      // Remove a mensagem de sucesso após 3 segundos
      setTimeout(() => {
        msg.textContent = "";
        msg.classList.remove("success");
        button.disabled = false;
      }, 2000);
      form.reset();
    } else {
      // Mostra uma mensagem de erro ao usuário
      const responseJson = await response.json();
      const error = responseJson;
      // Quando ocorre um erro, adiciona a classe 'invalid-input' ao input
      if (error.error === "name error") {
        inputName.classList.add("invalid-input");
      } else {
        inputName.classList.remove("invalid-input");
      }
      if (error.error === "email error") {
        inputEmail.classList.add("invalid-input");
      } else {
        inputEmail.classList.remove("invalid-input");
      }
      msg.textContent = error.message;
    }
  } catch (error) {
    msg.textContent = error.message;
  }
}

async function getEvidenceById(id) {
  const apiUrl = `http://localhost:8080/evidences/${id}`;
  try {
    const response = await fetchWithInterceptor(apiUrl, { method: "GET" });
    if (response.ok) {
      const evidence = await response.json();
      return evidence;
    }
  } catch (error) {
    throw new Error(`Error fetching evidence: ${error}`);
  }
}

async function fetchAnswersLikeTopicForButtonTds(idCompany, topic) {
  const apiUrl = `http://localhost:8080/answers/by-topic?idCompany=${idCompany}&topic=${topic}`;
  try {
    const response = await fetchWithInterceptor(apiUrl, { method: "GET" });

    if (!response.ok) {
      throw new Error("Error fetching answers");
    }

    const responseData = await response.json();
    const answerMap = {};
    responseData.forEach((answer) => {
      answerMap[answer.idQuestion] = answer;
    });
    return answerMap;
  } catch (error) {
    console.error(`Error fetching answers: ${error.message}`);
    alert("Error fetching answers");
  }
}

async function fetchQuestionsBySummaryAndPage(idSummary, page, pageSize) {
  const apiUrl = `http://localhost:8080/questions/summaries/${idSummary}?page=${page}&size=${pageSize}`;
  try {
    const response = await fetchWithInterceptor(apiUrl, { method: "GET" });
    if (!response.ok) {
      throw new Error("Error fetching API data");
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error fetching API data:", error);
    return {
      totalPages: 0,
      content: [],
    };
  }
}

async function uploadFile(file, idAnswer, downloadLink, deleteButton, index) {
  const apiUrl = "http://localhost:8080/evidences/upload";
  const formData = new FormData();
  formData.append("file", file);
  formData.append("idAnswer", idAnswer);

  try {
    const response = await fetchWithInterceptor(apiUrl, {
      method: "POST",
      body: formData,
    });
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    getEvidenceById(idAnswer).then((evidence) => {
      if (evidence) {
        downloadLink.innerText = evidence.name;
        downloadLink.classList.remove("disabled");
        downloadLink.setAttribute("href", "#");
        deleteButton.disabled = false;
        const radio1 = document.querySelector(
          `input[name="resp${index}"][value="1"]`
        );
        const radio2 = document.querySelector(
          `input[name="resp${index}"][value="2"]`
        );
        radio1.disabled = true;
        radio2.disabled = true;
      }
    });
    return await response.json();
  } catch (error) {
    console.error(`Error: ${error}`);
  }
}

async function downloadFile(idEvidence) {
  // Faz a requisição para o endpoint de download
  const apiUrl = `http://localhost:8080/evidences/download/${idEvidence}`;
  try {
    const response = await fetchWithInterceptor(apiUrl, { method: "GET" });
    // Verifica se o header Content-Disposition está presente na resposta
    if (!response.headers.has("content-disposition")) {
      throw new Error("Não foi possível obter o nome do arquivo");
    }

    // Obtém o nome do arquivo a partir do header Content-Disposition
    const contentDisposition = response.headers.get("content-disposition");
    const filenameMatch = contentDisposition.match(/attachment;filename=(.+)/);
    if (!filenameMatch) {
      throw new Error("Nome do arquivo não encontrado");
    }
    const filename = filenameMatch[1];

    // Obtém o tipo do arquivo a partir do header Content-Type

    // Cria um objeto Blob a partir do corpo da resposta
    const blob = await response.blob();
    // Cria uma URL para o objeto Blob
    const createUrl = URL.createObjectURL(blob);
    // Cria um link para fazer o download do arquivo
    const link = document.createElement("a");
    link.href = createUrl;
    link.download = filename;
    // Adiciona o link à página e simula um clique nele
    link.click();
    // Remove o link da página e revoga a URL
    URL.revokeObjectURL(createUrl);
  } catch (error) {
    console.error(error);
  }
}

async function deleteFile(idEvidence, downloadLink, deleteButton, index) {
  const apiUrl = `http://localhost:8080/evidences/delete/${idEvidence}`;
  try {
    const response = await fetchWithInterceptor(apiUrl, { method: "DELETE" });
    if (response.ok) {
      downloadLink.innerText = "Inclua evidência";
      downloadLink.classList.add("disabled");
      downloadLink.removeAttribute("href");
      deleteButton.disabled = true;
      const radio1 = document.querySelector(
        `input[name="resp${index}"][value="1"]`
      );
      const radio2 = document.querySelector(
        `input[name="resp${index}"][value="2"]`
      );
      radio1.disabled = false;
      radio2.disabled = false;
      console.log("Evidência deletada com sucesso");
    }
  } catch (error) {
    console.error(error);
  }
}

async function fetchAnswers() {
  const apiUrl = "http://localhost:8080/answers";
  try {
    const response = await fetchWithInterceptor(apiUrl, { method: "DELETE" });

    if (!response.ok) {
      throw new Error("Error fetching answers");
    }
    const responseData = await response.json();
    return responseData;
  } catch (error) {
    console.error(`Error fetching answers: ${error.message}`);
    alert("Error fetching answers");
  }
}

export {
  fetchEmployee,
  searchCep,
  login,
  registerCompany,
  getDepartments,
  registerEmployee,
  getEvidenceById,
  fetchAnswersLikeTopicForButtonTds,
  fetchQuestionsBySummaryAndPage,
  uploadFile,
  downloadFile,
  deleteFile,
  fetchAnswers,
};
