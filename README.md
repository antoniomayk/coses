# Coses: Robust Task Management API with Spring Boot

Coses is a Spring Boot application built for managing tasks effectively. It
offers a REST API and leverages various technologies for **robustness**,
**scalability**, and **easy testing**.

## Key Features

* **CRUD operations**             : Create, Read, Update, and Delete tasks
* **Task status management**      : Update task status independently
* **Comprehensive documentation** : Swagger UI and OpenAPI spec for clear API usage
* **Modular and containerized**   : Docker & Docker Compose for easy deployment and scaling
* **Modern technologies**         : Spring Boot, JPA, PostgreSQL, and more for performance and maintainability

## Technologies

* **Spring Boot**     : Build foundation for web application and REST API
* **Spring Boot JPA** : Simplify data persistence with JPA and relational databases
* **Spring Web**      : Handle web functionalities and requests
* **PostgreSQL**      : Secure and reliable relational database for data storage
* **Docker**          : Containerize the application for portability and isolation
* **Docker Compose**  : Manage multi-container deployments seamlessly
* **Flyway**          : Automate database migrations for smooth updates
* **Testcontainers**  : Utilize Docker containers for realistic integration tests
* **JUnit**           : Unit testing framework for code coverage and quality assurance
* **MockK**           : Flexible mocking framework for unit test isolation
* **Gradle**          : Manage build automation and dependencies efficiently
* **Swagger**         : Generate interactive API documentation for developers

## Endpoints

The API provides the following endpoints for managing tasks

| OPERATION | PATH                      | DESCRIPTION                   |
|-----------|---------------------------|-------------------------------|
| GET       | /api/v1/tasks/{id}        | Get a specific task by its ID |
| GET       | /api/v1/tasks             | Retrieve all tasks            |
| POST      | /api/v1/tasks             | Create a new task             |
| PUT       | /api/v1/tasks/{id}        | Update task information       |
| PUT       | /api/v1/tasks/{id}/status | Update task status only       |
| DELETE    | /api/v1/tasks/{id}        | Delete a specific task        |

## Getting Started

1. Ensure you have Docker and Docker Compose installed.
2. Clone this repository and run ``./coses``
3. Access Swagger UI for API documentation at http://localhost:8080/swagger-ui/index.html.

## Documentation

Detailed API documentation, including request and response formats, is available
in ``./build/openapi.json``. Generate it using ``./gradlew clean generateOpenApiDocs``.

## Testing

Run unit tests with ``./gradlew test``

## Customization

Replace the PostgreSQL configuration in docker-compose.yml for other supported
databases.
Adjust application settings in ``src/main/resources/application.properties`` as needed.
