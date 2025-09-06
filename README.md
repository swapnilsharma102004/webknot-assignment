# JustForLearningHibernate

This is a beginner-friendly Java project built to demonstrate how to use **Hibernate ORM** with **PostgreSQL**. It includes the basic setup to connect a Java application to a PostgreSQL database using Hibernate and Maven.

This project is great for students or anyone trying to understand how ORM (Object-Relational Mapping) works using Hibernate in Java.

---

##  Technologies Used

- Java 17+
- Hibernate ORM
- PostgreSQL
- JDBC
- Maven
- IntelliJ IDEA (Recommended IDE)

---

##  Project Structure

```

JustForLearning/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── hibernateLearning/
│       │           ├── Main.java
│       │           └── Student.java
│       └── resources/
│           └── hibernate.cfg.xml
├── pom.xml

````

- `Main.java`: Entry point of the application
- `Student.java`: Hibernate entity class
- `hibernate.cfg.xml`: Hibernate and database configuration
- `pom.xml`: Maven file for managing dependencies

---

##  Prerequisites

Before you begin, make sure you have the following installed:

### ✅ Java 17 or higher  
Download: https://www.oracle.com/java/technologies/javase-downloads.html

### ✅ PostgreSQL  
Download: https://www.postgresql.org/download/

> ⚠️ Don't forget to note your PostgreSQL username, password, and database name. You'll need them in the config.

### ✅ Maven  
Download: https://maven.apache.org/download.cgi

---

##  How to Set Up & Run

### ✅ 1. Clone the Repository

```bash
git clone https://github.com/swapnilsharma102004/JustForLearning.git
cd JustForLearning
````

### ✅ 2. Open the Project in IntelliJ

* Go to **File → Open** and select the `JustForLearning` folder
* Let Maven import all dependencies

### ✅ 3. Configure `hibernate.cfg.xml`

Edit `src/main/resources/hibernate.cfg.xml` with your PostgreSQL credentials:

```xml
<property name="hibernate.connection.driver_class">org.postgresql.Driver</property>
<property name="hibernate.connection.url">jdbc:postgresql://localhost:5432/your_database</property>
<property name="hibernate.connection.username">your_username</property>
<property name="hibernate.connection.password">your_password</property>
<property name="hibernate.dialect">org.hibernate.dialect.PostgreSQLDialect</property>
```

Make sure PostgreSQL is running and the database exists.

---

## ▶️ Running the Application

Once your configuration is set:

* Open `Main.java`
* Click **Run** or right-click and choose **Run 'Main.main()'**
* You’ll see Hibernate output and DB operations in the console

---

##  Contributing

If you'd like to improve this project, feel free to fork it, make changes, and submit a pull request.

---

##  Contact

Created by [Swapnil Sharma](https://github.com/swapnilsharma102004)

---

## ⭐️ Support

If you found this helpful, give it a ⭐️ on GitHub — it helps others discover the project too!

