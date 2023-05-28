import { fetchWithInterceptor } from "./module/utils/interceptor.js";
import {
  downloadFile,
  deleteFile,
  fetchEmployee,
  uploadFile,
  getEvidenceById,
  fetchAnswersLikeTopicForButtonTds,
  fetchQuestionsBySummaryAndPage,
} from "./module/api.js";
import { expirationTime, tokenNotExists } from "./module/utils/token.js";

let selectedSummary = 0;
let currentPages = {};
let currentSummary = 0;
let summaries = [];

let option = parseInt(sessionStorage.getItem("option")) || 5;

const allowedExtensions = [
  ".pdf",
  ".doc",
  ".docx",
  ".xls",
  ".xlsx",
  ".ppt",
  ".pptx",
  ".txt",
];

//dropdown.value = selectedSummary.topic.idTopic;
const token = localStorage.getItem("token");
const rowsPerPage = 3;
const prevButton = document.getElementById("prev-button");
const nextButton = document.getElementById("next-button");
const prevButtonSummary = document.getElementById("prev-button-summary");
const nextButtonSummary = document.getElementById("next-button-summary");

const organizationalControls = document.getElementById(
  "organizational-controls"
);
const controlsForPeople = document.getElementById("controls-for-people");
const physicalControls = document.getElementById("physical-controls");
const technologicalControls = document.getElementById("technological-controls");

const summary = document.getElementById("summary");
const topic = document.getElementById("topic");
const dropdown = document.getElementById("topic-dropdown");

let previousSelectedTopicId = parseInt(dropdown.value);

dropdown.addEventListener("change", () => {
  const selectedTopicId = parseInt(dropdown.value);
  if (selectedTopicId !== previousSelectedTopicId) {
    currentSummary = summaries.findIndex(
      (summary) => summary.idTopic === selectedTopicId

    );
    previousSelectedTopicId = selectedTopicId;
  } else {
    currentSummary = summaries.findIndex((summary) => summary.idTopic);
  }
  console.clear();
  updateTable();
});

prevButton.addEventListener("click", () => {
  currentPages[selectedSummary.idSummary]--;
  console.clear();
  updateTable();
});

nextButton.addEventListener("click", () => {
  currentPages[selectedSummary.idSummary]++;
  console.clear();
  updateTable();
});

nextButtonSummary.addEventListener("click", () => {
  if (currentSummary < summaries.length - 1) {
    currentSummary++;
    dropdown.value = summaries[currentSummary].idTopic;
    console.clear();
    updateTable();
  }
});

prevButtonSummary.addEventListener("click", () => {
  if (currentSummary > 0) {
    currentSummary--;
    dropdown.value = summaries[currentSummary].idTopic;
    console.clear();
    updateTable();
  }
});

organizationalControls.addEventListener("click", () => {
  variableInitialization();
  option = 5;
  sessionStorage.setItem("option", option);
  console.clear();
  updateDropdown();
  updateTable();
});

controlsForPeople.addEventListener("click", () => {
  variableInitialization();
  option = 6;
  sessionStorage.setItem("option", option);
  console.clear();
  updateDropdown();
  updateTable();
});

physicalControls.addEventListener("click", () => {
  variableInitialization();
  option = 7;
  sessionStorage.setItem("option", option);
  console.clear();
  updateDropdown();
  updateTable();
});

technologicalControls.addEventListener("click", () => {
  variableInitialization();
  option = 8;
  sessionStorage.setItem("option", option);
  console.clear();
  updateDropdown();
  updateTable();
});

async function fetchSummaries(topic) {
  const url = `http://localhost:8080/summaries/${topic}`;
  try {
    const response = await fetchWithInterceptor(url, { method: "GET" });
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

const fetchQuestionsBySummaryAndPage = async (idSummary, page, pageSize) => {
  try {
    const response = await fetch(
      `http://localhost:8080/questions/summaries/${idSummary}?page=${page}&size=${pageSize}`
    );
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
};

async function fetchTopics(topic) {
  const url = `http://localhost:8080/topics/${topic}`;

  try {
    const response = await fetch(url);
    const data = await response.json();
    return data;
  } catch (error) {
    console.error(`Error fetching topics: ${error.message}`);
  }
}

async function updateDropdown() {
  try {
    const topic = option;
    const data = await fetchTopics(topic);
    dropdown.innerHTML = "";
    data.forEach((topic) => {
      const option = document.createElement("option");
      option.value = topic.idTopic;
      option.textContent = topic.text;
      dropdown.appendChild(option);
    });
  } catch (error) {
    console.error(`Error updating dropdown: ${error.message}`);
  }
}

async function updateTable() {
  try {
    const employee = await fetchEmployee(token);
    const companyId = employee.company.idCompany;
    const topic = option;
    summaries = await fetchSummaries(topic);
    selectedSummary = summaries[currentSummary];
    if (!selectedSummary) {
      console.log(`Summary for topic not found`);
      return;
    }
    const data = await fetchQuestionsBySummaryAndPage(
      selectedSummary.idSummary,
      currentPages[selectedSummary.idSummary],
      rowsPerPage
    );
    const answers = await fetchAnswersLikeTopicForButtonTds(companyId, topic);
    displayQuestions(data, answers);
    await fillQuestionnaireWithAnswers(data);
  } catch (error) {
    console.error("Error while updating the table:", error);
  }
}

function displayQuestions(data, answers) {
  topic.innerText = selectedSummary.topic;
  summary.innerText = selectedSummary.text;
  const totalPages = data.totalPages;
  prevButton.disabled = currentPages[selectedSummary.idSummary] === 0;
  nextButton.disabled =
    currentPages[selectedSummary.idSummary] === totalPages - 1;
  prevButtonSummary.disabled = currentSummary === 0;
  nextButtonSummary.disabled = currentSummary === summaries.length - 1;
  const items = data.content;
  const firstRowIndex =
    currentPages[selectedSummary.idSummary] * rowsPerPage + 1;
  const tbody = document.getElementById("table-body");
  tbody.innerHTML = "";
  items.forEach((item, i) => {
    const answer = answers[item.idQuestion];
    const row = createTableRow(item, i, answer, firstRowIndex);
    tbody.appendChild(row);
  });
}

function createTableRow(item, index, answer, firstRowIndex) {
  const row = document.createElement("tr");
  const countTd = createCountTd(index, firstRowIndex);
  if (countTd) row.appendChild(countTd);

  const titleTd = createTitleTd(item.question, item.idQuestion);
  if (titleTd) row.appendChild(titleTd);

  const cellTds = createRadioInputTds(item, index);
  if (cellTds) {
    cellTds.forEach((cellTd) => {
      if (cellTd) row.appendChild(cellTd);
    });
  }

  const buttonTd = createButtonTd(answer, index, item);
  if (buttonTd) row.appendChild(buttonTd);

  return row;
}

function createCountTd(index, firstRowIndex) {
  const countTd = document.createElement("td");
  const countSpan = document.createElement("span");
  countTd.classList.add("table-count");
  countSpan.innerText = firstRowIndex + index;
  countTd.appendChild(countSpan);
  return countTd;
}

function createTitleTd(question, questionId) {
  const titleTd = document.createElement("td");
  titleTd.innerText = question;
  titleTd.setAttribute("data-question-id", questionId);
  return titleTd;
}

function createRadioInputTds(item, index) {
  const tds = [];
  for (let j = 1; j <= 4; j++) {
    const cellTd = document.createElement("td");
    const radioInput = createRadioInput(item, index, j);
    cellTd.appendChild(radioInput);
    tds.push(cellTd);
  }
  return tds;
}

function createRadioInput(item, index, optionIndex) {
  const radioInput = document.createElement("input");
  radioInput.type = "radio";
  radioInput.name = `resp${index}`;
  radioInput.id = "radio-input";
  radioInput.value = optionIndex;

  fetchEmployee(token)
    .then((employee) => {
      const companyId = employee.company.idCompany;
      // rest of your code using companyId
      radioInput.addEventListener("click", () => {
        const questionId = item.idQuestion;
        const data = {
          idQuestion: questionId,
          idCompany: companyId,
          notApplicable: optionIndex === 1 ? true : false,
          notMet: optionIndex === 2 ? true : false,
          partiallyMet: optionIndex === 3 ? true : false,
          fullyMet: optionIndex === 4 ? true : false,
        };
        createAnswer(data);
        const uploadButton = document.querySelector(`#upload${index}`);
        if (optionIndex === 3 || optionIndex === 4) {
          if (uploadButton) {
            uploadButton.disabled = false;
          }
        } else {
          if (uploadButton) {
            uploadButton.disabled = true;
          }
        }
      });
    })
    .catch((error) => {
      console.error(error);
    });
  return radioInput;
}

function createButtonTd(answer, index, item) {
  const buttonTd = document.createElement("td");
  const downloadLink = document.createElement("a");
  const uploadButton = document.createElement("button");
  const deleteButton = document.createElement("button");
  downloadLink.innerText = "Inclua evidência";
  downloadLink.classList.add("disabled");
  downloadLink.removeAttribute("href");
  uploadButton.classList.add("upload-button");
  uploadButton.innerText = "Enviar/Atualizar";
  uploadButton.disabled = true;
  uploadButton.id = `upload${index}`;
  deleteButton.classList.add("delete-button");
  deleteButton.innerText = "Excluir";
  deleteButton.disabled = true;

  if (answer) {
    const optionIndex = getOptionIndex(
      answer.notApplicable,
      answer.notMet,
      answer.partiallyMet,
      answer.fullyMet
    );
    if (optionIndex === 3 || optionIndex === 4) {
      uploadButton.disabled = false;
    } else {
      uploadButton.disabled = true;
    }
    getEvidenceById(answer.idAnswer)
      .then((evidence) => {
        const radio1 = document.querySelector(
          `input[name="resp${index}"][value="1"]`
        );
        const radio2 = document.querySelector(
          `input[name="resp${index}"][value="2"]`
        );
        if (evidence) {
          downloadLink.classList.remove("disabled");
          downloadLink.setAttribute("href", "#");
          deleteButton.disabled = false;
          downloadLink.innerText = evidence.name;
          radio1.disabled = true;
          radio2.disabled = true;
        } else {
          downloadLink.classList.add("disabled");
          downloadLink.removeAttribute("href");
          deleteButton.disabled = true;
          radio1.disabled = false;
          radio2.disabled = false;
        }
      })
      .catch((error) => {
        console.error("Ocorreu um erro ao obter a evidência: ", error);
      });
  } else {
    uploadButton.disabled = true;
  }

  uploadButton.addEventListener("click", async () => {
    console;
    if (answer) {
      selectAndUploadFile(answer.idAnswer, downloadLink, deleteButton, index);
    } else {
      const answer = await getAnswerByQuestionId(item);
      selectAndUploadFile(answer.idAnswer, downloadLink, deleteButton, index);
    }
  });

  downloadLink.addEventListener("click", async () => {
    if (answer) {
      await downloadFile(answer.idAnswer);
    } else {
      const answer = await getAnswerByQuestionId(item);
      await downloadFile(answer.idAnswer);
    }
  });

  deleteButton.addEventListener("click", async () => {
    const popupContainer = document.getElementById("popup-container");
    const closeButton = document.getElementById("close-button");
    popupContainer.style.display = "flex";

    const confirmButton = document.getElementById("confirm-button");
    const cancelButton = document.getElementById("cancel-button");

    closeButton.addEventListener("click", () => {
      popupContainer.style.display = "none";
    });

    popupContainer.addEventListener("click", (event) => {
      if (event.target === popupContainer) {
        popupContainer.style.display = "none";
      }
    });

    confirmButton.addEventListener("click", async () => {
      if (answer) {
        const evidence = await getEvidenceById(answer.idAnswer);
        if (evidence) {
          await deleteFile(
            evidence.idEvidence,
            downloadLink,
            deleteButton,
            index
          );
        }
      } else {
        const answer = await getAnswerByQuestionId(item);
        const evidence = await getEvidenceById(answer.idAnswer);
        if (evidence) {
          await deleteFile(
            evidence.idEvidence,
            downloadLink,
            deleteButton,
            index
          );
        }
      }

      popupContainer.style.display = "none";
    });

    cancelButton.addEventListener("click", () => {
      popupContainer.style.display = "none";
    });
  });

  buttonTd.appendChild(downloadLink);
  buttonTd.appendChild(uploadButton);
  buttonTd.appendChild(deleteButton);

  return buttonTd;
}

async function getAnswerByQuestionId(item) {
  const questionId = item.idQuestion;
  const employee = await fetchEmployee(token);
  const companyId = employee.company.idCompany;
  const answers = await fetchAnswersLikeTopicForButtonTds(companyId, option);
  const answer = answers[questionId];
  return answer;
}

function selectAndUploadFile(idAnswer, downloadLink, deleteButton, index) {
  const fileInput = document.createElement("input");
  fileInput.type = "file";
  fileInput.accept = ".pdf, .doc, .docx, .xls, .xlsx, .ppt, .pptx, .txt"; // tipos de arquivos permitidos
  fileInput.size = 5 * 1024 * 1024;
  fileInput.addEventListener("change", async () => {
    const file = fileInput.files[0];
    if (file) {
      if (
        !allowedExtensions.some((ext) => file.name.toLowerCase().endsWith(ext))
      ) {
        alert("The selected file is not allowed.");
        fileInput.value = "";
      } else if (file.size > fileInput.size) {
        alert("The selected file is larger than the maximum allowed size.");
        fileInput.value = "";
      } else {
        await uploadFile(file, idAnswer, downloadLink, deleteButton, index);
      }
    }
  });
  fileInput.click();
}

async function fetchAnswersLikeTopic(idCompany, topic) {
  const url = `http://localhost:8080/answers/by-topic?idCompany=${idCompany}&topic=${topic}`;
  try {
    const response = await fetch(url);

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

async function fillQuestionnaireWithAnswers(data) {
  try {
    const employee = await fetchEmployee(token);
    const companyId = employee.company.idCompany;
    const topic = option;
    const answers = await fetchAnswersLikeTopic(companyId, topic);
    const items = data.content;

    for (let i = 0; i < items.length; i++) {
      const questionId = items[i].idQuestion;
      const questionTitleCell = document.querySelector(
        `[data-question-id="${questionId}"]`
      );

      const answer = answers.find((answer) => {
        return answer.idQuestion === questionId;
      });

      if (answer) {
        const optionIndex = getOptionIndex(
          answer.notApplicable,
          answer.notMet,
          answer.partiallyMet,
          answer.fullyMet
        );
        const radioInput = document.querySelector(
          `input[name="resp${i}"][value="${optionIndex}"]`
        );
        if (questionTitleCell && radioInput) {
          radioInput.checked = true;
        }
      } else {
        const radioInput = document.querySelector(`input[name="resp${i}"]`);
        if (questionTitleCell && radioInput) {
          radioInput.checked = false;
        }
      }
    }
  } catch (error) {
    console.error("Error in 'fillQuestionnaireWithAnswers' function:", error);
  }
}

function getOptionIndex(notApplicable, notMet, partiallyMet, fullyMet) {
  if (notApplicable) {
    return 1;
  } else if (notMet) {
    return 2;
  } else if (partiallyMet) {
    return 3;
  } else if (fullyMet) {
    return 4;
  }
}

async function createAnswer(data) {
  const url = "http://localhost:8080/answers";
  try {
    const response = await fetch(url, {
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

async function fetchQuestions() {
  const url = "http://localhost:8080/questions";
  try {
    const response = await fetch(url);

    if (!response.ok) {
      throw new Error("Error fetching questions");
    }

    const responseData = await response.json();
    return responseData;
  } catch (error) {
    console.error(`Error fetching questions: ${error.message}`);
    alert("Error fetching questions");
  }
}

function variableInitialization() {
  selectedSummary = 0;
  currentPages = {};
  currentSummary = 0;
  summaries = [];
}

let isFetching = false;
async function init() {
  if (isFetching) {
    return; // Se já houver uma solicitação em andamento, ignorar
  }

  isFetching = true;
  tokenNotExists(token);
  expirationTime(token);
  await wait(1000);
  await updateDropdown();
  await updateTable();
  isFetching = false;
}

document.addEventListener("DOMContentLoaded", function () {
  init();
});

function wait(ms) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}
