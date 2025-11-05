document.getElementById('loginForm').addEventListener('submit', async (e) => {
  e.preventDefault();

  const email = document.getElementById('email').value;
  const password = document.getElementById('password').value;

  const response = await fetch('http://localhost:8080/api/auth/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email, password })
  });

  if (response.ok) {
    const data = await response.json();
    localStorage.setItem('token', data.token);
    window.location.href = 'index.html';
  } else {
    document.getElementById('errorMsg').textContent = 'Invalid credentials!';
  }
});

function logout() {
  localStorage.removeItem('token');
  window.location.href = 'login.html';
}
