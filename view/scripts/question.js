import {
  downloadFile,
  deleteFile,
  fetchEmployee,
  uploadFile,
  getEvidenceById,
  fetchAnswersLikeTopicForButtonTds,
  fetchQuestionsBySummaryAndPage,
  createAnswer,
  fetchTopics,
  fetchSummaries,
  fetchAnswersLikeTopic,
  fetchAnswerCountByIdCompany,
} from "./module/api.js";
import {
  expirationTime,
  tokenNotExists,
  logout,
  handleToken,
} from "./module/utils/token.js";
import {
  resultQuestion,
  updateIframeVarCompany,
} from "./module/utils/grafana.js";

const totalAnswer = 319;
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
  ".csv",
  ".jpg",
  ".png",
];

const companyName = document.getElementById("company-name");
const employeeName = document.querySelector(".employee-name");
const logoutButton = document.getElementById("logout-btn");
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
const resultButton = document.getElementById("result");

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

logoutButton.addEventListener("click", () => {
  logout();
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
    summaries = await fetchSummaries(topic, summaries, currentPages);
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
        createAnswer(data).then(() => {
          if (!sessionStorage.getItem("congratulation")) {
            fetchAnswerCountByIdCompany(companyId).then((answerCount) => {
              if (answerCount === totalAnswer) {
                congratulationPopup(companyId);
                sessionStorage.setItem("congratulation", true);
              }
            });
          }
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
      selectAndUploadFile(
        answer.idAnswer,
        downloadLink,
        deleteButton,
        index,
        uploadButton
      );
    } else {
      const answer = await getAnswerByQuestionId(item);
      selectAndUploadFile(
        answer.idAnswer,
        downloadLink,
        deleteButton,
        index,
        uploadButton
      );
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

    let confirmClicked = false; // Variável de controle

    closeButton.addEventListener("click", () => {
      if (confirmClicked) return;

      confirmClicked = true;

      popupContainer.style.display = "none";
    });

    popupContainer.addEventListener("click", (event) => {
      if (confirmClicked) return;
      if (event.target === popupContainer) {
        popupContainer.style.display = "none";
        confirmClicked = true;
      }
    });

    confirmButton.addEventListener("click", async () => {
      if (confirmClicked) return;

      confirmClicked = true;

      if (answer) {
        const evidence = await getEvidenceById(answer.idAnswer);
        if (evidence) {
          await deleteFile(
            evidence.idEvidence,
            downloadLink,
            deleteButton,
            index,
            confirmButton,
            uploadButton
          );
        }
      } else {
        const answerByQuestionId = await getAnswerByQuestionId(item);
        const evidence = await getEvidenceById(answerByQuestionId.idAnswer);
        if (evidence) {
          await deleteFile(
            evidence.idEvidence,
            downloadLink,
            deleteButton,
            index,
            confirmButton,
            uploadButton
          );
        }
      }

      popupContainer.style.display = "none";
    });

    cancelButton.addEventListener("click", () => {
      if (confirmClicked) return;
      popupContainer.style.display = "none";

      confirmClicked = true;
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

function selectAndUploadFile(
  idAnswer,
  downloadLink,
  deleteButton,
  index,
  uploadButton
) {
  const fileInput = document.createElement("input");
  fileInput.type = "file";
  fileInput.accept =
    ".pdf, .doc, .docx, .xls, .xlsx, .ppt, .pptx, .txt, .csv, .jpg, .png";
  fileInput.size = 50 * 1024 * 1024;
  fileInput.addEventListener("change", async () => {
    const file = fileInput.files[0];
    if (file) {
      if (
        !allowedExtensions.some((ext) => file.name.toLowerCase().endsWith(ext))
      ) {
        alert("A extensão do arquivo não é permitida.");
        fileInput.value = "";
      } else if (file.size > fileInput.size) {
        alert("O Arquivo é maior do que 50MB");
        fileInput.value = "";
      } else {
        await uploadFile(
          file,
          idAnswer,
          downloadLink,
          deleteButton,
          index,
          uploadButton
        );
      }
    }
  });
  fileInput.click();
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

function variableInitialization() {
  selectedSummary = 0;
  currentPages = {};
  currentSummary = 0;
  summaries = [];
}

async function checkAnswerCount(idCompany) {
  const answerCount = await fetchAnswerCountByIdCompany(idCompany);

  if (
    answerCount === totalAnswer &&
    !sessionStorage.getItem("congratulation")
  ) {
    congratulationPopup(idCompany);
    sessionStorage.setItem("congratulation", true);
  }
}

function congratulationPopup(idCompany) {
  const popupContainer = document.getElementById("congratulations-popup");
  const popupCloseButton = document.getElementById("popup-close");
  const popupResultButton = document.getElementById("result-popup");
  resultQuestion(idCompany, popupResultButton);
  updateIframeVarCompany(idCompany);

  popupContainer.style.display = "block";

  popupContainer.addEventListener("click", function (event) {
    if (event.target === popupContainer) {
      popupContainer.style.display = "none";
    }
  });

  popupCloseButton.addEventListener("click", function () {
    popupContainer.style.display = "none";
  });
}

let isFetching = false;
async function init() {
  tokenNotExists(token);
  expirationTime(token);
  const idCompany = handleToken(token).tokenIdCompany;
  companyName.textContent = handleToken(token).tokenCompany;
  employeeName.textContent = handleToken(token).tokenName;
  await updateDropdown();
  await resultQuestion(idCompany, resultButton);
  await updateTable();
  await checkAnswerCount(idCompany);
}

document.addEventListener("DOMContentLoaded", function () {
  init();
});

function wait(ms) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}
