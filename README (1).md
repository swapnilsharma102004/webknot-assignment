# Webknot Assignment – Project Documentation

## Project Understanding and Initial Steps
When I received the assignment, I started by carefully going through the requirements and understanding the problem statement.  
To get more clarity, I designed an ER diagram to map out the entities and their relationships. This step helped me visualize the data flow and overall structure of the project.

After that, I consolidated the requirements and the PDF document provided by the company. I used those details to generate a suitable prompt with the help of Gemini Pro, so I could quickly bootstrap the development environment using Windsurf.

---

## Choice of Tech Stack
Since I had some prior experience with **Java Spring Boot**, I decided to use it for the backend.  
For the frontend, I chose **React** because of its flexibility and ease of integration.  
As for the database, I selected **PostgreSQL** since it is reliable and scalable for future use cases.

This combination provided a solid full-stack setup:  
- **Backend:** Java Spring Boot  
- **Frontend:** React  
- **Database:** PostgreSQL  

---

## Development Process
1. **Backend Setup**  
   I used Windsurf to generate the initial Spring Boot code and then made edits to align with the requirements.  
   Since I was already familiar with running Spring Boot applications, setting up and executing the backend was straightforward.

2. **Frontend Setup**  
   Once the backend was running, I shifted to the frontend and integrated it with the API.  
   To simplify testing, I kept the project basic and preloaded three students and one admin in the database.  
   (Note: This is not a recommended practice in real projects, but it made the demo easier to test.)

3. **Handling Challenges**  
   During development, I encountered issues like **CORS policy errors**.  
   Fortunately, since I had worked on a similar problem before, I was able to resolve it by configuring CORS in the Spring Boot backend.  
   Once that was fixed, the frontend and backend started working together as expected.

---

## Current State of the Project
- The system has **basic authentication** with pre-created users (3 students, 1 admin).  
- User registration was intentionally not implemented, since it will be handled by the company as part of their review process.  
- The project is functional and demonstrates how the stack works together.

---

## How to Run

### Prerequisites
- **Java 21** or higher  
- **Maven**  
- **Node.js** and **npm**  
- **PostgreSQL** running locally  

---

### Backend (Spring Boot)
1. Clone the repository:  
   ```bash
   git clone https://github.com/swapnilsharma102004/webknot-assignment.git
   cd webknot-assignment
   ```
2. Configure database settings in `application.properties` (PostgreSQL username, password, and database name).  
3. Build and run the backend:  
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
4. The backend will start on:  
   ```
   http://localhost:8080
   ```

---

### Frontend (React)
1. Navigate to the frontend folder:  
   ```bash
   cd frontend
   ```
2. Install dependencies:  
   ```bash
   npm install
   ```
3. Start the development server:  
   ```bash
   npm start
   ```
4. The frontend will run on:  
   ```
   http://localhost:3000
   ```

---

## Default Login Credentials
To make testing easier, the following users are already preloaded into the system:

### Admin
- **Username:** admin1  
- **Password:** admin123  

### Students
- **Username:** student1 | **Password:** student123  
- **Username:** student2 | **Password:** student123  
- **Username:** student3 | **Password:** student123  

---

## Reflection
The project gave me an opportunity to apply my Spring Boot knowledge while also practicing full-stack development with React and PostgreSQL.  
Although I leveraged code generation tools to speed up the setup, I ensured that I understood the flow, made necessary edits, and handled key challenges like CORS.  

This approach helped me deliver a working solution within the given timeline while keeping it scalable for future improvements.
