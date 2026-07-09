// base url for the backend, change this if your spring boot app runs on a different port
const API_URL = "http://localhost:8080";

function showError(message) {
  alert(message);
}

function showSuccess(message) {
  alert(message);
}

// saves the JWT token we get after login into the browser storage
function saveToken(token, role) {
  localStorage.setItem("token", token);
  localStorage.setItem("role", role);
}

function getToken() {
  return localStorage.getItem("token");
}

function getRole() {
  return localStorage.getItem("role");
}

function isLoggedIn() {
  return getToken() != null;
}

// used before calling any protected api, sends the token in the header
function getHeaders() {
  return {
    Authorization: "Bearer " + getToken(),
  };
}

// call this at the top of pages that need login
function checkLogin() {
  if (!isLoggedIn()) {
    showError("Please login first");
    window.location.href = "login.html";
  }
}

function logout() {
  localStorage.removeItem("token");
  localStorage.removeItem("role");
  window.location.href = "login.html";
}
