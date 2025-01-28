# City Finder API

City Finder API is a Spring Boot application that provides functionality for finding city information, including auto-complete suggestions based on user input. This API allows users to search for cities by name and retrieve relevant details such as population, geographical coordinates, and more.

## Prerequisites

- Java 21 or later
- Maven (for building and running the project)
- A JDK (Java Development Kit) installed on your system

## Getting Started

Follow the steps below to get the application up and running on your local machine.

### 1. Clone the Repository

Clone the repository using the following command:

```bash
git clone https://github.com/yourusername/city-finder-api.git
```
### 2. Navigate to the Project Directory
```bash
cd city-finder-api
```
### 3. Build the Application
You can build the application using Maven:
```bash
mvn clean install
```
### 4. Run the Application
To run the application locally, use the following command:
```bash
mvn spring-boot:run
```
Alternatively, you can run the packaged JAR file:
```bash
java -jar target/city-finder-api-0.0.1-SNAPSHOT.jar
```

### Swagger UI

City Finder API integrates Swagger UI for interactive documentation. You can access the Swagger UI at the following URL:
```bash 
http://localhost:8080/swagger-ui/index.html
```

### Running Tests
To run the unit tests for the application, use the following command:
```bash 
mvn test
```


Thank You 
