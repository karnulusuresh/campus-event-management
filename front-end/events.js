document.addEventListener("DOMContentLoaded", () => {
  loadEvents();

  document.getElementById("createEventForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const newEvent = {
      title: document.getElementById("title").value,
      description: document.getElementById("description").value,
      startDateTime: document.getElementById("startDateTime").value,
      endDateTime: document.getElementById("endDateTime").value,
      location: document.getElementById("location").value,
      categoryName: document.getElementById("categoryName").value,
      createdByUserName: document.getElementById("createdByUserName").value
    };

    try {
      const token = localStorage.getItem("jwt"); // assuming auth token needed

      const res = await fetch("http://localhost:8080/api/events", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(newEvent)
      });

      if (!res.ok) {
        const errorData = await res.json();
        throw new Error(errorData.message || "Failed to create event");
      }

      document.getElementById("eventMsg").textContent = "Event created successfully!";
      document.getElementById("createEventForm").reset();
      loadEvents(); // refresh event list

    } catch (err) {
      document.getElementById("eventMsg").style.color = "red";
      document.getElementById("eventMsg").textContent = err.message;
    }
  });
});

async function loadEvents() {
  try {
    const token = localStorage.getItem("jwt");

    const res = await fetch("http://localhost:8080/api/events", {
      method: "GET",
      headers: { "Authorization": `Bearer ${token}` }
    });
    if (!res.ok) throw new Error("Failed to fetch events");

    const events = await res.json();
    console.log(events);
    const container = document.querySelector(".event-cards");
    container.innerHTML = "";

    events.forEach(event => {
      const card = document.createElement("article");
      card.classList.add("event-card");
      // Add extra class if event is Music Concert
      if (event.title === "Music Concert") {
        card.classList.add("music-concert");
      }
      // if (event.title === "Inter-collegiate Football Finals") {
      //   card.classList.add("art-exhibition");
      // }
      // if (event.title === "Tech Conference") {
      //   card.classList.add("tech-conference");
      // }
      // if (event.title === "Food Festival") {
      //   card.classList.add("food-festival");
      // }
      // if
      card.innerHTML = `
        <h3 class="event-title">${event.title}</h3>
        <p class="event-category">Category: ${event.categoryName || "N/A"}</p>
        <p class="event-description">${event.description}</p>
        <p class="event-time">
          <strong>Start:</strong> ${formatDate(event.startDateTime)}<br />
          <strong>End:</strong> ${formatDate(event.endDateTime)}
        </p>
        <p class="event-location"><strong>Location:</strong> ${event.location}</p>
        <button class="btn register-btn" data-eventid="${event.id}" data-eventtitle="${event.title}">Register</button>
      `;

      container.appendChild(card);
    });

    // Add Register button event listeners
    document.querySelectorAll(".register-btn").forEach(btn => {
      btn.addEventListener("click", function () {
        const eventId = this.getAttribute("data-eventid");
        const eventTitle = this.getAttribute("data-eventtitle");
        // Redirect to registration page passing eventId and title as query params
        window.location.href = `registrations.html?eventId=${eventId}&eventTitle=${encodeURIComponent(eventTitle)}`;
      });
    });

  } catch (err) {
    console.error(err);
    document.querySelector(".event-cards").innerHTML = "<p>Failed to load events.</p>";
  }
}


function formatDate(dateStr) {
  const options = {
    year: "numeric", month: "short", day: "numeric",
    hour: "2-digit", minute: "2-digit"
  };
  return new Date(dateStr).toLocaleDateString(undefined, options);
}
