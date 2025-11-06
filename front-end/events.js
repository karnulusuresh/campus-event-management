async function loadEvents() {
  const token = localStorage.getItem('token');
  if (!token) return window.location.href = 'login.html';

  const response = await fetch('http://localhost:8080/api/events', {
    headers: { 'Authorization': `Bearer ${token}` }
  });

  if (!response.ok) {
    alert('Failed to fetch events. Please log in again.');
    return window.location.href = 'login.html';
  }

  const events = await response.json();
  const tbody = document.querySelector('#eventTable tbody');
  tbody.innerHTML = '';

  events.forEach(e => {
    const row = `
      <tr>
        <td>${e.id}</td>
        <td>${e.title}</td>
        <td>${e.categoryName}</td>
        <td>${e.startDateTime}</td>
        <td>${e.endDateTime}</td>
        <td>${e.location}</td>
      </tr>`;
    tbody.insertAdjacentHTML('beforeend', row);
  });
}

async function createEvent(e) {
  e.preventDefault();
  const token = localStorage.getItem('token');

  const newEvent = {
    title: document.getElementById('title').value,
    description: document.getElementById('description').value,
    startDateTime: document.getElementById('startDateTime').value,
    endDateTime: document.getElementById('endDateTime').value,
    location: document.getElementById('location').value,
    categoryName: document.getElementById('categoryName').value
  };

  const response = await fetch('http://localhost:8080/api/events', {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(newEvent)
  });

  if (response.ok) {
    document.getElementById('eventMsg').textContent = 'Event created successfully!';
    document.getElementById('createEventForm').reset();
    loadEvents();
  } else {
    document.getElementById('eventMsg').textContent = 'Failed to create event.';
  }
}

window.onload = loadEvents;
document.getElementById('createEventForm').addEventListener('submit', createEvent);
