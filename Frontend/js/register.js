document.getElementById("registerForm").addEventListener("submit", register);

async function register(event) {
  event.preventDefault();

  let account = {
    username: document.getElementById("username").value.trim(),
    password: document.getElementById("password").value.trim(),
    fullName: document.getElementById("fullName").value.trim(),
    role: document.getElementById("role").value,
    phoneNumber: document.getElementById("phoneNumber").value.trim(),
    region: document.getElementById("region").value.trim(),
  };

  // basic validation
  if (account.username == "") {
    showError("Username is required");
    return;
  }
  if (account.password == "") {
    showError("Password is required");
    return;
  }
  if (account.fullName == "") {
    showError("Full Name is required");
    return;
  }

  try {
    await axios.post(API_URL + "/register", account);

    showSuccess("Registered Successfully. Please login now.");
    window.location.href = "login.html";
  } catch (error) {
    if (error.response) {
      showError(error.response.data || "Unable to Register");
    } else {
      showError("Server Not Reachable");
    }
  }
}
