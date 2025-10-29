# 🎓 Campus Event Management System (Spring Boot + MySQL)

## 🧾 Overview
The **Campus Event Management System** is a web-based application developed using **Spring Boot**, **Java**, and **MySQL**.  
It allows administrators and students to manage, register, and explore events within a campus environment.

The project demonstrates CRUD operations, RESTful APIs, database design, and full-stack architecture concepts suitable for academic and real-world use.

---

## ⚙️ Tech Stack
- **Backend:** Java 17, Spring Boot 3.5.7
- **Database:** MySQL 8.x
- **ORM:** Spring Data JPA (Hibernate)
- **Build Tool:** Maven
- **IDE:** Spring Tool Suite (STS)
- **Database Client:** DBeaver
- **Version Control:** Git + GitHub

---

## 🧩 Features
- 👩‍🎓 **User Management:** Register, update, and list users
- 🎉 **Event Management:** Create, update, view, and delete events
- 🏷️ **Categories:** Classify events by type (technical, cultural, sports, etc.)
- 📝 **Registrations:** Users can register for available events
- 🔐 **Role Management (future enhancement):** Admin / Student separation
- 🔍 **REST APIs:** Expose CRUD operations via RESTful endpoints

---

## 🏗️ Project Structure

event-management/
├── src/
│ ├── main/
│ │ ├── java/com/campus/event/
│ │ │ ├── controller/ # REST controllers (EventController, UserController, etc.)
│ │ │ ├── model/ # Entity classes (Event, User, Registration, Category)
│ │ │ ├── repository/ # JPA repositories (interfaces extending JpaRepository)
│ │ │ ├── service/ # Business logic (EventService, UserService, etc.)
│ │ │ └── EventManagementApplication.java # Main Spring Boot application
│ │ └── resources/
│ │ ├── application.properties # DB config and Spring settings
│ │ └── static/ # Frontend assets (if any)
│ │ └── templates/ # HTML templates (if using Thymeleaf)
│ └── test/java/com/campus/event/
│ └── EventManagementApplicationTests.java
├── pom.xml # Maven dependencies
├── README.md # Project documentation
└── .gitignore # Ignored files (target/, .idea/, etc.)


---

## 🗄️ Database Design (ERD Overview)

### Entities:
1. **User**
   - `user_id` (PK)
   - `name`
   - `email`
   - `password`
   - `role` (e.g., STUDENT / ADMIN)

2. **Category**
   - `category_id` (PK)
   - `name`
   - `description`

3. **Event**
   - `event_id` (PK)
   - `title`
   - `description`
   - `date`
   - `location`
   - `category_id` (FK → Category)

4. **Registration**
   - `registration_id` (PK)
   - `user_id` (FK → User)
   - `event_id` (FK → Event)
   - `registration_date`

### Relationships:
- A `User` can register for many `Events`
- An `Event` belongs to one `Category`
- A `Category` can have multiple `Events`
- A `Registration` connects `User` ↔ `Event`

---

## 🧰 Database Configuration (application.properties)

- `spring.datasource.url=jdbc:mysql://localhost:3306/campus_event_db?useSSL=false&serverTimezone=UTC`
- `spring.datasource.username=campus_user`
- `spring.datasource.password=StrongPassword123!`
- `spring.jpa.hibernate.ddl-auto=update`
- `spring.jpa.show-sql=true`
- `spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect`
- `server.port=8080`

---

## 🚀 How to Run the Application
🧱 Prerequisites

**Java 17 or higher**

**MySQL Server (running)**

**Maven installed**

**STS IDE or IntelliJ**

**DBeaver (for DB access)**

---

## ▶️ Steps to Run
# Clone the repository
git clone https://github.com/<your-username>/event-management.git
cd event-management

# Run the app
./mvnw spring-boot:run
The app starts on: http://localhost:8080

---

### 🧪 Example API Endpoints
Method	Endpoint	Description
GET	/api/events	Get all events
POST	/api/events	Create new event
GET	/api/users	Get all users
POST	/api/registrations	Register user for event

---

## 📘 Example cURL Command
curl -X POST http://localhost:8080/api/events \
-H "Content-Type: application/json" \
d '{"title": "Tech Fest 2025", "description": "Annual technology fest", "date": "2025-11-10", "category_id": 1}'

---

## 🧑‍💻 Developer Info

Name: Karnulu Suresh

Department: ECE (B.Tech)

Focus Areas: Java, Spring Boot, MySQL, Full Stack Development

---

## 🧠 Future Enhancements

-🔐 Authentication (JWT or Spring Security)

-📩 Email notifications for registrations

-📊 Admin dashboard for reports

-🌐 Frontend (React.js or Thymeleaf)

---

##📄 License

MIT License © 2025 Karnulu Suresh