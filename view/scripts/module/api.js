import { fetchWithInterceptor } from "./utils/interceptor.js";

const HTTP_PORT = 8080;

async function fetchEmployee(token) {
  const employeeId = JSON.parse(atob(token.split(".")[1])).id;
  const apiUrl = `http://localhost:${HTTP_PORT}/tcc-audit/employee/${employeeId}`;
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

async function fetchEmployees() {
  const apiUrl = `http://localhost:${HTTP_PORT}/tcc-audit/employees`;
  try {
    const response = await fetchWithInterceptor(apiUrl, { method: "GET" });
    if (response.ok) {
      const data = await response.json();
      return data;
    } else {
      console.error(`Error fetching employee: ${response.status}`);
      throw new Error("Failed to fetch employees");
    }
  } catch (error) {
    console.error(`Error fetching employee: ${error.message}`);
  }
}

async function fetchTopics(topic) {
  const apiUrl = `http://localhost:${HTTP_PORT}/tcc-audit/topics/${topic}`;
  try {
    const response = await fetchWithInterceptor(apiUrl, { method: "GET" });
    const data = await response.json();
    return data;
  } catch (error) {
    console.error(`Error fetching topics: ${error.message}`);
  }
}

async function fetchSummaries(topic, summaries, currentPages) {
  const apiUrl = `http://localhost:${HTTP_PORT}/tcc-audit/summaries/${topic}`;
  try {
    const response = await fetchWithInterceptor(apiUrl, { method: "GET" });
    const data = await response.json();
    const summariesObj = {}; // Criar um objeto vazio
    data.forEach((summary) => {
      if (!(summary.idSummary in summariesObj)) {
        // Usar o objeto vazio
        summariesObj[summary.idSummary] = summary;
        currentPages[summary.idSummary] ||= 0;
      }
    });
    summaries = Object.values(summariesObj); // Converter de volta para um array
    return summaries;
  } catch (error) {
    console.error(`Error fetching summaries: ${error.message}`);
  }
}

async function login(email, password, loginForm) {
  const error = document.querySelector("#error");
  const apiUrl = `http://localhost:${HTTP_PORT}/tcc-audit/login`;
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
  companyName,
  cnpjInput,
  cepInput,
  inputEmail,
  email,
  password
) {
  const msg = document.querySelector("#msg");
  const apiUrl = `http://localhost:${HTTP_PORT}/tcc-audit/sign-up-company`;
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
          name: companyName,
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

  const apiUrl = `http://localhost:${HTTP_PORT}/tcc-audit/via-cep?postalCode=${cep}`;
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
  const apiUrl = `http://localhost:${HTTP_PORT}/tcc-audit/departments`;
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
  const apiUrl = `http://localhost:${HTTP_PORT}/tcc-audit/sign-up`;
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
  const apiUrl = `http://localhost:${HTTP_PORT}/tcc-audit/evidences/${id}`;
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
  const apiUrl = `http://localhost:${HTTP_PORT}/tcc-audit/answers/by-topic?idCompany=${idCompany}&topic=${topic}`;
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
  const apiUrl = `http://localhost:${HTTP_PORT}/tcc-audit/questions/summaries/${idSummary}?page=${page}&size=${pageSize}`;
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

async function createAnswer(data) {
  const apiUrl = `http://localhost:${HTTP_PORT}/tcc-audit/answers`;
  try {
    const response = await fetchWithInterceptor(apiUrl, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });

    if (!response.ok) {
      throw new Error("Error creating answer");
    }
    const responseData = await response.json();
    return responseData;
  } catch (error) {
    console.error(`Error creating answer: ${error.message}`);
    alert("Error creating answer");
  }
}

async function uploadFile(
  file,
  idAnswer,
  downloadLink,
  deleteButton,
  index,
  uploadButton
) {
  const apiUrl = `http://localhost:${HTTP_PORT}/tcc-audit/evidences/upload`;
  const formData = new FormData();
  formData.append("file", file);
  formData.append("idAnswer", idAnswer);

  // Adicionar classe para indicar o upload em andamento
  downloadLink.innerText = "Enviando...";
  downloadLink.classList.add("uploading");

  uploadButton.disabled = true;

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
    uploadButton.disabled = false;
    return await response.json();
  } catch (error) {
    downloadLink.innerText = "Inclua evidência";
    uploadButton.disabled = false;
    console.error(`Error: ${error}`);
  } finally {
    // Remover a classe de upload em andamento
    downloadLink.classList.remove("uploading");
  }
}

async function downloadFile(idEvidence) {
  // Faz a requisição para o endpoint de download
  const apiUrl = `http://localhost:${HTTP_PORT}/tcc-audit/evidences/download/${idEvidence}`;
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

async function deleteFile(
  idEvidence,
  downloadLink,
  deleteButton,
  index,
  confirmButton,
  uploadButton
) {
  const apiUrl = `http://localhost:${HTTP_PORT}/tcc-audit/evidences/delete/${idEvidence}`;
  downloadLink.innerText = "Deletando...";
  try {
    // Adicionar classe para indicar a exclusão em andamento
    confirmButton.classList.add("deleting");
    confirmButton.disabled = true;
    uploadButton.disabled = true;
    downloadLink.classList.add("disabled");

    const response = await fetchWithInterceptor(apiUrl, { method: "DELETE" });
    if (response.ok) {
      downloadLink.innerText = "Inclua evidência";
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
      confirmButton.disabled = false;
      uploadButton.disabled = false;
      console.log("Evidência deletada com sucesso");
    }
  } catch (error) {
    downloadLink.innerText = "Inclua evidência";
    confirmButton.disabled = false;
    uploadButton.disabled = false;
    console.error(error);
  } finally {
    // Remover a classe de exclusão em andamento
    confirmButton.classList.remove("deleting");
  }
}

async function fetchAnswersLikeTopic(idCompany, topic) {
  const apiUrl = `http://localhost:${HTTP_PORT}/tcc-audit/answers/by-topic?idCompany=${idCompany}&topic=${topic}`;
  try {
    const response = await fetchWithInterceptor(apiUrl, { method: "GET" });

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

async function fetchAnswerCountByIdCompany(idCompany) {
  const apiUrl = `http://localhost:${HTTP_PORT}/tcc-audit/answers/count/${idCompany}`;

  try {
    const response = await fetchWithInterceptor(apiUrl, { method: "GET" });

    if (!response.ok) {
      throw new Error("Error fetching answer count");
    }

    const responseData = await response.json();
    return responseData;
  } catch (error) {
    console.error(`Error fetching answer count: ${error.message}`);
    alert("Error fetching answer count");
  }
}

async function updateEmployeeStatus(employee) {
  const apiUrl = `http://localhost:${HTTP_PORT}/tcc-audit/is-enabled/${employee.idEmployee}`;
  const isEnable = employee.isEnabled;
  try {
    const response = await fetchWithInterceptor(apiUrl, { method: "PUT" });
    if (response.ok) {
      employee.isEnabled = !isEnable;
    } else {
      console.error(`Error updating employee status: ${response.status}`);
      throw new Error("Falha ao atualizar o status do funcionário");
    }
  } catch (error) {
    console.error(`Error updating employee status: ${error.message}`);
    throw error;
  }
}

export {
  fetchEmployee,
  fetchEmployees,
  fetchTopics,
  fetchSummaries,
  searchCep,
  login,
  registerCompany,
  getDepartments,
  registerEmployee,
  getEvidenceById,
  fetchAnswersLikeTopicForButtonTds,
  fetchQuestionsBySummaryAndPage,
  createAnswer,
  uploadFile,
  downloadFile,
  deleteFile,
  fetchAnswersLikeTopic,
  fetchAnswerCountByIdCompany,
  updateEmployeeStatus,
};
