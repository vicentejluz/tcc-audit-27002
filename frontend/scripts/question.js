import { fetchWithInterceptor } from "./module/utils/interceptor.js";

let selectedSummary = 0;
let currentPages = {};
let currentSummary = 0;
let summaries = [];
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

//dropdown.value = selectedSummary.topic.idTopic; depois ver isso

const rowsPerPage = 3;
const prevButton = document.getElementById("prev-button");
const nextButton = document.getElementById("next-button");
const prevButtonSummary = document.getElementById("prev-button-summary");
const nextButtonSummary = document.getElementById("next-button-summary");
const summary = document.getElementById("summary");
const topic = document.getElementById("topic");
const dropdown = document.getElementById("topic-dropdown");

let previousSelectedTopicId = parseInt(dropdown.value);

dropdown.addEventListener("change", () => {
  const selectedTopicId = parseInt(dropdown.value);
  if (selectedTopicId !== previousSelectedTopicId) {
    currentSummary = summaries.findIndex(
      (summary) => summary.topic.idTopic === selectedTopicId
    );
    previousSelectedTopicId = selectedTopicId;
  }
  updateTable();
});

prevButton.addEventListener("click", () => {
  currentPages[selectedSummary.idSummary]--;
  updateTable();
});

nextButton.addEventListener("click", () => {
  currentPages[selectedSummary.idSummary]++;
  updateTable();
});

nextButtonSummary.addEventListener("click", () => {
  if (currentSummary < summaries.length - 1) {
    currentSummary++;
    updateTable();
  }
});

prevButtonSummary.addEventListener("click", () => {
  if (currentSummary > 0) {
    currentSummary--;
    updateTable();
  }
});

async function fetchSummaries() {
  const url = "http://localhost:8080/questions";
  try {
    const response = await fetchWithInterceptor(url, { method: "GET" });
    const data = await response.json();
    const summariesObj = {}; // Criar um objeto vazio
    data.forEach((question) => {
      if (!(question.summary.idSummary in summariesObj)) {
        // Usar o objeto vazio
        summariesObj[question.summary.idSummary] = question.summary;
        currentPages[question.summary.idSummary] ||= 0;
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

async function fetchTopics() {
  const url = "http://localhost:8080/topics";
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
    const data = await fetchTopics();
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
    summaries = await fetchSummaries();
    const selectedTopicId = parseInt(dropdown.value);
    selectedSummary = summaries.find(
      (summary) => summary.topic.idTopic === selectedTopicId
    );
    if (!selectedSummary) {
      console.log(`Summary for topic ${selectedTopicId} not found`);
      return;
    }
    selectedSummary = summaries[currentSummary];
    const data = await fetchQuestionsBySummaryAndPage(
      selectedSummary.idSummary,
      currentPages[selectedSummary.idSummary],
      rowsPerPage
    );
    displayQuestions(data);
    await fillQuestionnaireWithAnswers(data);
  } catch (error) {
    console.error("Error while updating the table:", error);
  }
}

function displayQuestions(data) {
  topic.innerText = selectedSummary.topic.text;
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
    const row = createTableRow(item, i, firstRowIndex);
    tbody.appendChild(row);
  });
}

function createTableRow(item, index, firstRowIndex) {
  const row = document.createElement("tr");
  const countTd = createCountTd(index, firstRowIndex);
  const titleTd = createTitleTd(item.question, item.idQuestion);
  const cellTds = createRadioInputTds(item, index);
  const fileTd = createFileInputTd(item, index);
  row.appendChild(countTd);
  row.appendChild(titleTd);
  cellTds.forEach((cellTd) => {
    row.appendChild(cellTd);
  });
  row.appendChild(fileTd);
  return row;
}

function createCountTd(index, firstRowIndex) {
  const countTd = document.createElement("td");
  const countSpan = document.createElement("span");
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

function createFileInputTd(index) {
  const cellTd = document.createElement("td");
  const fileInput = document.createElement("input");
  fileInput.type = "file";
  fileInput.name = `file${index}`;
  fileInput.accept = ".pdf, .doc, .docx, .xls, .xlsx, .ppt, .pptx, .txt";
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
        await uploadFile(file, fileInput);
      }
    }
  });
  cellTd.appendChild(fileInput);
  return cellTd;
}

function createRadioInput(item, index, optionIndex) {
  const radioInput = document.createElement("input");
  radioInput.type = "radio";
  radioInput.name = `resp${index}`;
  radioInput.value = optionIndex;

  fetchEmployee()
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
      });
    })
    .catch((error) => {
      console.error(error);
    });
  return radioInput;
}

function getPropertyName(optionIndex) {
  switch (optionIndex) {
    case 1:
      return "notApplicable";
    case 2:
      return "notMet";
    case 3:
      return "partiallyMet";
    case 4:
      return "fullyMet";
    default:
      return "";
  }
}

async function fillQuestionnaireWithAnswers(data) {
  try {
    const answers = await fetchAnswers();
    const items = data.content;

    for (let i = 0; i < items.length; i++) {
      const questionId = items[i].idQuestion;
      const questionTitleCell = document.querySelector(
        `[data-question-id="${questionId}"]`
      );
      const employee = await fetchEmployee();
      const companyId = employee.company.idCompany;

      const answer = answers.find((answer) => {
        return (
          answer.question.idQuestion === questionId &&
          answer.company.idCompany === companyId
        );
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

async function fetchAnswers() {
  const url = "http://localhost:8080/answers";
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

async function uploadFile(file, fileInput) {
  const url = "http://localhost:8080/upload";
  const formData = new FormData();
  formData.append("file", file);
  try {
    const response = await fetch(url, {
      method: "POST",
      body: formData,
    });
    if (!response.ok) {
      throw new Error("Error uploading file");
    }
    const responseData = await response.json();
    return responseData;
  } catch (error) {
    console.error(`Error uploading file: ${error.message}`);
    alert("Error uploading file");
    //fileInput.value = "";
  }
}

async function fetchEmployee() {
  const token = localStorage.getItem("token");
  const employeeId = JSON.parse(atob(token.split(".")[1])).id;
  const url = `http://localhost:8080/employee/${employeeId}`;
  try {
    const response = await fetchWithInterceptor(url, { method: "GET" });
    const data = await response.json();
    return data;
  } catch (error) {
    console.error(`Error fetching usuário: ${error.message}`);
  }
}

async function init() {
  await updateDropdown();
  await updateTable();
}

init();
