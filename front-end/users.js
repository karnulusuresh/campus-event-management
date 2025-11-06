async function loadUsers() {
  const token = localStorage.getItem('token');
  if (!token) return window.location.href = 'login.html';

  const response = await fetch('http://localhost:8080/api/users', {
    headers: { 'Authorization': `Bearer ${token}` }
  });

  const users = await response.json();
  const tbody = document.querySelector('#userTable tbody');
  tbody.innerHTML = '';

  users.forEach(user => {
    const row = `<tr>
        <td>${user.userId}</td>
        <td>${user.name}</td>
        <td>${user.email}</td>
        <td>${user.userRole}</td>
      </tr>`;
    tbody.insertAdjacentHTML('beforeend', row);
  });
}

window.onload = loadUsers;
