let selectedSummary = 0;
let currentPages = {};
let currentSummary = 0;
let summaries = [];

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
    const response = await fetch(url);
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

async function updateDropdown() {
  const url = "http://localhost:8080/topics";
  try {
    const response = await fetch(url);
    const data = await response.json();
    dropdown.innerHTML = "";
    data.forEach((topic) => {
      const option = document.createElement("option");
      option.value = topic.idTopic;
      option.textContent = topic.text;
      dropdown.appendChild(option);
    });
  } catch (error) {
    console.error(`Error fetching topics: ${error.message}`);
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
    teste(data);
    displayQuestions(data);
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
  fileInput.disabled = false;
  cellTd.appendChild(fileInput);
  return cellTd;
}

function createRadioInput(item, index, optionIndex) {
  const radioInput = document.createElement("input");
  radioInput.type = "radio";
  radioInput.name = `resp${index}`;
  radioInput.value = optionIndex;
  console.log("function " + radioInput.name);
  radioInput.addEventListener("click", () => {
    const questionId = item.idQuestion;
    const companyId = 1;
    const value = getPropertyName(optionIndex);
    const data = {
      idQuestion: questionId,
      idCompany: companyId,
      notApplicable: value === "notApplicable" ? true : false,
      notMet: value === "notMet" ? true : false,
      partiallyMet: value === "partiallyMet" ? true : false,
      fullyMet: value === "fullyMet" ? true : false,
    };
    createAnswer(data);
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
      throw new Error("Erro ao criar resposta");
    }

    const responseData = await response.json();
    return responseData;
  } catch (error) {
    console.error(`Erro ao criar resposta: ${error.message}`);
    alert("Erro ao criar resposta");
  }
}

async function fetchAnswers() {
  const url = "http://localhost:8080/answers";
  try {
    const response = await fetch(url);

    if (!response.ok) {
      throw new Error("Erro ao buscar respostas");
    }

    const responseData = await response.json();
    return responseData;
  } catch (error) {
    console.error(`Erro ao buscar respostas: ${error.message}`);
    alert("Erro ao buscar respostas");
  }
}

async function fetchQuestions() {
  const url = "http://localhost:8080/questions";
  try {
    const response = await fetch(url);

    if (!response.ok) {
      throw new Error("Erro ao buscar respostas");
    }

    const responseData = await response.json();
    return responseData;
  } catch (error) {
    console.error(`Erro ao buscar respostas: ${error.message}`);
    alert("Erro ao buscar respostas");
  }
}

async function teste(data) {
  try {
    const answers = await fetchAnswers();
    const items = data.content;

    for (let i = 0; i < items.length; i++) {
      const questionId = items[i].idQuestion;
      const questionTitleCell = document.querySelector(
        `[data-question-id="${questionId}"]`
      );
      const answer = answers.find(
        (answer) => answer.question.idQuestion === questionId
      );

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
        console.log("Teste: " + radioInput.name);
        if (questionTitleCell && radioInput) {
          radioInput.checked = true;
          questionTitleCell.classList.add("has-response");
        }
      } else {
        const radioInput = document.querySelector(`input[name="resp${i}"]`);
        console.log("Teste1: " + radioInput.name);
        if (questionTitleCell && radioInput) {
          radioInput.checked = false;
          questionTitleCell.classList.remove("has-response");
        }
      }
    }
  } catch (error) {
    console.error("Error in 'teste' function:", error);
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
  } else {
    return null;
  }
}

async function init() {
  await updateDropdown();
  await updateTable();
}

init();
