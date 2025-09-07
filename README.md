# Webknot Assignment – Project Documentation

## Project Understanding and Initial Steps
When I got the assignment, I began by reviewing the requirements and understanding the problem statement. To gain more clarity, I created an ER diagram to outline the entities and their relationships. This step helped me see the data flow and the overall structure of the project.

Next, I combined the requirements with the PDF document from the company. I used that information to create a suitable prompt with Gemini Pro, allowing me to quickly set up the development environment using Windsurf.

---

## Choice of Tech Stack
Since I had some prior experience With **Java Spring Boot**, I chose it for the backend. For the frontend, I picked **React** because it is flexible and easy to integrate. For the database, I selected **PostgreSQL** since it is reliable and can grow with future needs.

This combination gave me a strong full-stack setup:  
- **Backend:** Java Spring Boot  
- **Frontend:** React  
- **Database:** PostgreSQL    

---

## Development Process
1. **Backend Setup**  
I used Windsurf to create the initial Spring Boot code and then made some edits to meet the requirements. Since I was already familiar with running Spring Boot applications, setting up and running the backend was simple.

2. **Frontend Setup**  
Once the backend was running, I moved to the frontend and connected it with the API. To make testing easier, I kept the project simple and added three students and one admin to the database. (Note: This is not a good practice in real projects, but it made the demo simpler to test.)

3. **Handling Challenges**  
During development, I ran into issues like **CORS policy errors**. Fortunately, I had dealt with a similar problem before, so I fixed it by configuring CORS in the Spring Boot backend. Once that was resolved, the frontend and backend started working together correctly.

---

## Current State of the Project
- The system has **basic authentication** with pre-created users (3 students, 1 admin).  
- I did not implement user registration because the company will handle it as part of their review process.  
- The project works and shows how the stack operates together.
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
The project allowed me to use my Spring Boot knowledge and practice full-stack development with React and PostgreSQL. I used code generation tools to speed up the setup. However, I made sure to understand the flow, make necessary edits, and address key challenges like CORS. 

This method helped me deliver a working solution on time while keeping it scalable for future improvements.
