import { tokenNotFound } from "./module/utils/token.js";

const token = localStorage.getItem("token");

async function init() {
  tokenNotFound(token);
}

init();
