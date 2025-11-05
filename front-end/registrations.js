async function loadRegistrations() {
  const token = localStorage.getItem('token');
  if (!token) return window.location.href = 'login.html';

  const response = await fetch('http://localhost:8080/api/registrations', {
    headers: { 'Authorization': `Bearer ${token}` }
  });

  if (!response.ok) {
    alert('Failed to fetch registrations.');
    return;
  }

  const regs = await response.json();
  const tbody = document.querySelector('#registrationTable tbody');
  tbody.innerHTML = '';

  regs.forEach(r => {
    const row = `
      <tr>
        <td>${r.id}</td>
        <td>${r.userName} (#${r.userId})</td>
        <td>${r.eventTitle} (#${r.eventId})</td>
        <td>${r.status}</td>
        <td>${r.registeredAt}</td>
      </tr>`;
    tbody.insertAdjacentHTML('beforeend', row);
  });
}

async function registerUser(e) {
  e.preventDefault();
  const token = localStorage.getItem('token');

  const payload = {
    userId: parseInt(document.getElementById('userId').value),
    eventId: parseInt(document.getElementById('eventId').value)
  };

  const response = await fetch('http://localhost:8080/api/registrations', {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(payload)
  });

  if (response.ok) {
    document.getElementById('regMsg').textContent = 'User registered successfully!';
    document.getElementById('registerForm').reset();
    loadRegistrations();
  } else {
    document.getElementById('regMsg').textContent = 'Registration failed.';
  }
}

window.onload = loadRegistrations;
document.getElementById('registerForm').addEventListener('submit', registerUser);
