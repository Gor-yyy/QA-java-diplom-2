# QA Java Diplom 2 — API Test Automation

## Project Overview

This project contains automated API tests for the Stellar Burgers application. The tests verify user registration, authentication, and order creation under different conditions.

The project uses REST Assured to send HTTP requests and validate API responses, JUnit 4 to organize and execute tests, and Allure to collect test execution results.

## Tested Functionality

### User Registration
- Verify successful registration of a new user.
- Verify that registering an existing user returns an error.
- Verify that registration fails when the email, password, or name is missing.

### User Authentication
- Verify successful login with valid credentials.
- Verify that login fails when the email or password is incorrect.

### Order Creation
- Verify order creation by an authenticated user.
- Verify order creation without authentication.
- Verify that creating an order without ingredients returns an error.
- Verify the server response when invalid ingredient IDs are provided.

### Test Data Management
- Generate unique email addresses for test users.
- Delete created test users after applicable test scenarios.

## Technologies and Tools

- **Java 11** — programming language
- **Maven** — project and dependency management
- **JUnit 4.13.2** — test framework
- **REST Assured 5.5.0** — API testing
- **Jackson Databind 2.17.2** — JSON processing dependency
- **Allure 2.29.0** — test reporting
- **Maven Surefire 3.5.4** — test execution

## Requirements

To run the project, you need:

- JDK 11 or a compatible Java 11 development environment
- Apache Maven
- Internet access to reach the Stellar Burgers API

An IDE, such as IntelliJ IDEA, is optional.

## How to Run the Tests

1. Clone the repository:

   ```bash
   git clone -b develop2 https://github.com/Gor-yyy/QA-java-diplom-2.git
   ```

2. Navigate to the project directory:

   ```bash
   cd QA-java-diplom-2
   ```

3. Run the tests:

   ```bash
   mvn clean test
   ```

Maven will compile the project and execute the tests.

## Allure Test Report

The project is configured to save Allure test results in `target/allure-results`.

To generate and open an Allure report using the configured Maven plugin, run:

```bash
mvn allure:serve
```

If report generation fails, check the Allure plugin requirements and your local Java and Maven setup.

## Conclusion

This project demonstrates practical experience in API test automation, including HTTP request validation, positive and negative testing, parameterized tests, test data preparation, and cleanup. It also demonstrates the use of Maven and Allure in a Java testing project.
