// Base URL for backend
const BASE_URL = "http://localhost:8080/api";

// LOGIN
const loginForm = document.getElementById("loginForm");
if (loginForm) {
  loginForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    try {
      const res = await fetch(`${BASE_URL}/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
      });

      if (!res.ok) throw new Error("Invalid credentials");
      const data = await res.json();

      localStorage.setItem("jwt", data.token);
      alert("Login successful!");
      window.location.href = "index.html";
    } catch (err) {
      document.getElementById("errorMessage").textContent = err.message;
    }
  });
}

// SIGNUP
const signupForm = document.getElementById("signupForm");
if (signupForm) {
  signupForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const user = {
      name: document.getElementById("name").value,
      email: document.getElementById("email").value,
      password: document.getElementById("password").value,
      roleId: parseInt(document.getElementById("roleId").value),
    };

    try {
      const res = await fetch(`${BASE_URL}/users`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(user),
      });
      if (!res.ok) throw new Error("Failed to sign up");
      alert("Signup successful! Please login.");
      window.location.href = "login.html";
    } catch (err) {
      document.getElementById("errorMessage").textContent = err.message;
    }
  });
}

// LOGOUT
function logout() {
  localStorage.removeItem("jwt");
  alert("Logged out successfully.");
  window.location.href = "login.html";
}
