
  document.addEventListener("DOMContentLoaded", async () => {
    const token = localStorage.getItem("jwt");
    const res = await fetch("http://localhost:8080/api/users", {
      headers: { "Authorization": `Bearer ${token}` }
    });
    const users = await res.json();
    const tbody = document.querySelector("#usersTable tbody");

    tbody.innerHTML = ""; // Clear existing rows in case script runs multiple times

    users.forEach(u => {
      const tr = document.createElement("tr");

      tr.innerHTML = `
        <td>${u.userId}</td>
        <td>${u.name}</td>
        <td>${u.email}</td>
        <td>${u.userRole}</td>
        <td><button class="btn btn-small edit-btn" data-userid="${u.userId}">Edit</button></td>
      `;
      tbody.appendChild(tr);
    });

    // Add click event listeners to all edit buttons
    tbody.querySelectorAll(".edit-btn").forEach(button => {
      button.addEventListener("click", (event) => {
        const userId = event.target.getAttribute("data-userid");
        // Implement your edit logic here.
        // For example, redirect to an edit page with userId in the query param:
        window.location.href = `/edit-user.html?userId=${userId}`;

        // Or, you could open a modal for editing instead of redirecting.
      });
    });
  });

