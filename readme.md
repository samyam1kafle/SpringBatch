# ETLMS - Microservice Application

**ETLMS** is a microservice-based application built using **Spring Boot**. This guide provides step-by-step instructions to set up and run the application.

---

## Prerequisites

Before running the application, ensure you have the following:
- Java Development Kit (JDK) 17 or later
- Maven (latest version)
- A local database setup (e.g., MySQL, PostgreSQL)
- IDE (e.g., IntelliJ IDEA, Eclipse) or terminal for running services

---

## Steps to Run the Application

### 1. Update Dependencies
Update the Maven dependencies for all the services by syncing changes from the `pom.xml` file:

```bash
mvn clean install
```

---

### 2. Run the **ServerDiscovery** Service (Eureka Server)
Start the **ServerDiscovery** service first. This is the discovery client service used for registering all the other microservices.

---

### 3. Run the **ApiGateway** Service
Once the **Eureka server** is up and running, start the **ApiGateway** service.  
The routing configuration for all the services is defined here.

---

### 4. Run the **DataExtractionService**
The **DataExtractionService** is a Spring Batch demo service. Follow these steps:

1. **Configure the Database**:
   - Copy or rename the `application.yml.example` file to `application.yml`.
   - Update the `application.yml` file with your local database credentials:
     ```yaml
     spring:
       datasource:
         url: jdbc:mysql://localhost:3306/your_database
         username: your_username
         password: your_password
       jpa:
         hibernate:
           ddl-auto: update
         show-sql: true
     ```

2. **Run the Service**:  
   Once the database configuration is complete, start the **DataExtractionService**.

---

## Service Order

The microservices must be started in the following order:

1. **ServerDiscovery** (Eureka Server)
2. **ApiGateway** (Routing Service)
3. **DataExtractionService** (Spring Batch Service)

---

## Enjoy! 🎉
Your ETLMS microservice application is now up and running. Explore, test, and enhance as needed!
