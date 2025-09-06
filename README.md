# Eventzzz - Campus Event Management Platform

A comprehensive event management system for campus events with registration, attendance tracking, feedback collection, and detailed reporting capabilities.

## Tech Stack

### Backend
- **Java 17**
- **Spring Boot 3.x**
- **Maven** for dependency management
- **Spring Data JPA** for database operations
- **PostgreSQL** as the database
- **Lombok** for reducing boilerplate code

### Frontend
- **React 18** with modern hooks
- **Vite** for fast development and building
- **React Router DOM** for navigation
- **Axios** for API communication

## Project Structure

```
eventzzz/
├── eventzzz-backend/          # Spring Boot backend
│   ├── src/main/java/com/eventzzz/
│   │   ├── entity/            # JPA entities
│   │   ├── repository/        # Data repositories
│   │   ├── service/           # Business logic
│   │   ├── controller/        # REST controllers
│   │   ├── dto/              # Data transfer objects
│   │   ├── config/           # Configuration classes
│   │   └── exception/        # Exception handling
│   ├── src/main/resources/
│   │   └── application.yml   # Application configuration
│   └── pom.xml              # Maven dependencies
├── eventzzz-frontend/        # React frontend
│   ├── src/
│   │   ├── components/       # React components
│   │   ├── services/         # API service layer
│   │   └── main.jsx         # Application entry point
│   ├── package.json         # NPM dependencies
│   └── vite.config.js       # Vite configuration
├── queries.sql              # Documentation queries
└── README.md               # This file
```

## Database Schema

The system uses PostgreSQL with the following entities:

- **College**: Represents educational institutions
- **Student**: Student information linked to colleges
- **Event**: Campus events with types (WORKSHOP, FEST, SEMINAR, TECH_TALK)
- **Registration**: Links students to events with attendance and feedback

## Features

### Authentication & Authorization
- ✅ JWT-based authentication
- ✅ Role-based access control (Admin/Student)
- ✅ Secure login/logout functionality
- ✅ Protected routes and API endpoints

### Admin Features
- ✅ Create new events
- ✅ Delete events
- ✅ View all events with management controls
- ✅ Access to all reporting features

### Student Features
- ✅ Event registration
- ✅ Mark attendance for events
- ✅ Submit feedback (1-5 rating scale)
- ✅ View available events
- ✅ Access to reporting features

### Reporting Features
- ✅ Event summary reports (registrations, attendance %, avg feedback)
- ✅ Event popularity ranking
- ✅ Student participation history
- ✅ Top 3 most active students
- ✅ Event filtering by type

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Node.js 16+ and npm
- PostgreSQL 12+
- Maven 3.6+

### Database Setup

1. **Install PostgreSQL** and create a database:
   ```sql
   CREATE DATABASE eventzzz;
   CREATE USER postgres WITH PASSWORD 'password';
   GRANT ALL PRIVILEGES ON DATABASE eventzzz TO postgres;
   ```

2. **Update database configuration** in `eventzzz-backend/src/main/resources/application.yml`:
   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/eventzzz
       username: postgres
       password: password  # Change this to your password
   ```

### Backend Setup

1. **Navigate to backend directory**:
   ```bash
   cd eventzzz-backend
   ```

2. **Install dependencies and run**:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

3. **Verify backend is running**:
   - Backend will start on `http://localhost:8080`
   - Sample data will be automatically loaded
   - Check logs for "Sample data initialized successfully!"

### Frontend Setup

1. **Navigate to frontend directory**:
   ```bash
   cd eventzzz-frontend
   ```

2. **Install dependencies**:
   ```bash
   npm install
   ```

3. **Start development server**:
   ```bash
   npm run dev
   ```

4. **Access the application**:
   - Frontend will be available at `http://localhost:5173`
   - The app will automatically proxy API calls to the backend

## API Endpoints

### Event Management
- `POST /api/events/{eventId}/register` - Register student for event
- `POST /api/events/{eventId}/attendance` - Mark attendance
- `POST /api/events/{eventId}/feedback` - Submit feedback

### Reports
- `GET /api/reports/events/{eventId}/summary` - Event summary
- `GET /api/reports/popularity` - Event popularity ranking
- `GET /api/reports/students/{studentId}/participation` - Student participation
- `GET /api/reports/active-students` - Top 3 active students
- `GET /api/reports/events?type={eventType}` - Events with optional filtering

## Sample Data

The application comes with pre-loaded sample data:

### Colleges
- MIT College of Engineering
- Stanford University

### Students
- Alice Johnson (alice@mit.edu)
- Bob Smith (bob@mit.edu)
- Carol Davis (carol@stanford.edu)
- David Wilson (david@mit.edu)
- Eva Brown (eva@stanford.edu)

### Events
- Spring Boot Workshop (WORKSHOP)
- Tech Fest 2024 (FEST)
- AI/ML Seminar (SEMINAR)
- Cloud Computing Talk (TECH_TALK)
- Hackathon 2024 (WORKSHOP)

## Usage Guide

### Login Process
1. Open the application at `http://localhost:5173`
2. Use the demo credentials provided on the login screen:
   - **Admin**: username=`admin`, password=`admin123`
   - **Student**: username=`alice`, password=`alice123`
   - **Student**: username=`bob`, password=`bob123`

### Admin Functions
1. **Create Events**: Click "Create New Event" to add events
2. **Manage Events**: View all events and delete them if needed
3. **View Reports**: Access all reporting features
4. **Event Management**: Full CRUD operations on events

### Student Functions
1. **View Events**: Browse all available events
2. **Register**: Click "Register" for any event
3. **Mark Attendance**: Click "Mark Attendance" after attending
4. **Submit Feedback**: Rate events from 1-5 stars
5. **View Reports**: Access reporting features

### Reporting Features
- **Event Popularity**: Shows events ranked by registration count
- **Top Active Students**: Shows the 3 most active students
- **Student Participation**: Enter a student ID to see their event history
- **Event Summary**: Enter an event ID to see detailed statistics

## Development

### Backend Development
- The backend uses Spring Boot with auto-configuration
- JPA entities automatically create database tables
- Custom queries are defined in repository interfaces
- Global exception handling provides consistent error responses

### Frontend Development
- React components are organized by feature
- API calls are centralized in the services layer
- Responsive design works on desktop and mobile
- Error handling provides user-friendly messages

### Building for Production

**Backend**:
```bash
cd eventzzz-backend
mvn clean package
java -jar target/eventzzz-backend-0.0.1-SNAPSHOT.jar
```

**Frontend**:
```bash
cd eventzzz-frontend
npm run build
# Deploy the dist/ folder to your web server
```

## Troubleshooting

### Common Issues

1. **Database Connection Error**:
   - Ensure PostgreSQL is running
   - Check database credentials in application.yml
   - Verify database exists

2. **CORS Issues**:
   - Backend is configured to allow requests from localhost:5173
   - If using different ports, update CorsConfig.java

3. **Port Conflicts**:
   - Backend uses port 8080
   - Frontend uses port 5173
   - Change ports in application.yml and vite.config.js if needed

4. **Sample Data Not Loading**:
   - Check application logs for errors
   - Ensure database tables are created properly
   - Verify DataInitializer is running

### Logs and Debugging
- Backend logs show SQL queries and application events
- Frontend console shows API request/response details
- Use browser developer tools for debugging frontend issues

## Future Enhancements

Potential improvements for the system:
- User authentication and authorization
- Email notifications for event updates
- Event capacity limits and waitlists
- Advanced reporting with charts and graphs
- Mobile app development
- Integration with calendar systems
- Bulk operations for administrators

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is created for educational purposes as part of a campus event management system prototype.
