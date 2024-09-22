```markdown
# requirement-service-application

## Project Overview

The requirement-service-application is a Spring Boot-based application designed to help organizations find the right personnel based on their skill sets. It consists of two main services:

- **External Recruitment Service**
- **Internal Recruitment Service**

The application integrates with a MySQL database, implements a caching mechanism using Redis, and uses HTTP basic authentication. API documentation is provided using **OpenAPI Swagger**.

## Features

- **Skill-based Resource Search:** Quickly find resources within the organization based on specific skill sets.
- **MySQL Database Integration:** Store and manage user data and application configurations.
- **Redis Caching:** Enhance performance through effective caching mechanisms.
- **Secure Authentication:** Protect sensitive endpoints with HTTP basic authentication.
- **API Documentation:** Easily explore and interact with APIs via Swagger UI.

## Getting Started

### Prerequisites

To run this project locally, you will need:

- Java 17 or above
- Maven (for building and running the application)
- MySQL Database
- Redis (for caching)

### Database Setup

1. **Create a MySQL Database:**
   - Before starting the application, create a database named `requirement_service`.

2. **Run the SQL Script:**
   - Execute the SQL script present inside IMP DOCS directory to set up the necessary tables and default user/password: admin/fun123

  

### Configuration

Before running the application, configure the database and Redis settings in `application.properties` as per your conguration:

```properties
spring.application.name=RequirementServiceBackend

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/requirement_service
spring.datasource.username=springstudent
spring.datasource.password=springstudent


# Redis Configuration
spring.data.redis.host=localhost
spring.data.redis.port=6379
spring.cache.type=redis
```

### Running the Application

To run the Spring Boot application, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/AsifIqbalSekh/requirement-service-application.git
   cd requirement-service-application
   ```

2. Build the project with Maven:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

4. Access the Swagger UI for API documentation at:
   ```
   http://localhost:8080/swagger-ui/index.html
   ```

## API Documentation

The API documentation is automatically generated using OpenAPI (Swagger) and can be viewed in two ways:

1.	Locally: Once the application is running, navigate to:

```
           http://localhost:8080/swagger-ui/index.html

```
This provides a fully interactive interface to explore the APIs.

2.	Online (Hosted on GitHub Pages):
Even without running the application, you can view the API documentation hosted on GitHub Pages:

         https://asifiqbalsekh.github.io/requirement-service-application/swagger-ui/index.html 

## Folder Structure

```
	•	src/main/java: Contains the Java source code.
	•	src/main/resources: Contains configuration files such as application.properties.
	•	swagger-ui/: Contains the static Swagger UI files, including swagger.json for API documentation.
```

## Deployment

The project can be deployed to any cloud platform or server that supports Java and Spring Boot. To create a deployable `.jar` file, use the following command:

```bash
mvn clean package
```

This will generate a `.jar` file in the `target` directory, which can then be deployed to your server or cloud platform.

## Contributing

Feel free to submit pull requests or issues if you would like to contribute to this project.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
