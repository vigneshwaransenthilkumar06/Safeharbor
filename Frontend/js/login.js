

document.getElementById("loginForm").addEventListener("submit", login);

async function login(event) {
  event.preventDefault();

  let username = document.getElementById("username").value.trim();
  let password = document.getElementById("password").value.trim();

  if (username == "") {
    showError("Username is required");
    return;
  }
  if (password == "") {
    showError("Password is required");
    return;
  }

  let loginDTO = {
    username: username,
    password: password,
  };

  try {
    let response = await axios.post(API_URL + "/login", loginDTO);

    saveToken(response.data.token, response.data.role);

    showSuccess("Login Successful");
    window.location.href = "index.html";
  } catch (error) {
    if (error.response && error.response.status == 401) {
      showError("Invalid Username or Password");
    } else {
      showError("Unable to Connect to Server");
    }
  }
}
