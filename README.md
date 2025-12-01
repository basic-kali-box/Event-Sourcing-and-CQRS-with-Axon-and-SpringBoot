# Event Sourcing & CQRS with Axon and Spring Boot

This project demonstrates a **Bank Account Management** microservice architecture implementing **Event Sourcing** and **CQRS** (Command Query Responsibility Segregation) patterns using the **Axon Framework** and **Spring Boot**.

## 🚀 Overview

The application is designed to separate the **Write Model** (Commands) from the **Read Model** (Queries), ensuring scalability and flexibility.

-   **Command Side**: Handles state changes via Commands and Aggregates. Events are stored in an Event Store.
-   **Query Side**: Listens to events and updates a relational database (Projections) for efficient querying.
-   **Real-time Updates**: Uses Server-Sent Events (SSE) to push account updates to clients in real-time.

## 🛠️ Technologies

-   **Java 17**
-   **Spring Boot 3.2.2**
-   **Axon Framework 4.8.0**
-   **Spring Data JPA**
-   **H2 Database** (In-memory for Event Store and Read Model)
-   **Spring WebFlux** (For SSE)
-   **Lombok**
-   **SpringDoc OpenAPI** (Swagger UI)

## 🏗️ Architecture

### Command Model (Write)
-   **Aggregates**: `AccountAggregate`
-   **Commands**: `CreateAccountCommand`, `CreditAccountCommand`, `DebitAccountCommand`
-   **Events**: `AccountCreatedEvent`, `AccountActivatedEvent`, `AccountCreditedEvent`, `AccountDebitedEvent`

### Query Model (Read)
-   **Entities**: `Account`, `AccountOperation`
-   **Projections**: `AccountServiceHandler` updates the Read Database.
-   **Queries**: `GetAccountQuery`, `GetAllAccountsQuery`

## 🏃‍♂️ How to Run

1.  **Clone the repository**:
    ```bash
    git clone https://github.com/basic-kali-box/Event-Sourcing-and-CQRS-with-Axon-and-SpringBoot.git
    cd Event-Sourcing-and-CQRS-with-Axon-and-SpringBoot
    ```

2.  **Build the project**:
    ```bash
    ./mvnw clean package
    ```

3.  **Run the application**:
    ```bash
    ./mvnw spring-boot:run
    ```

The application will start on port `8082`.

## 📚 API Documentation

Swagger UI is available at:
👉 **[http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html)**

## 🧪 Testing the API

### 1. Create an Account
```bash
curl -X POST "http://localhost:8082/commands/account/create" \
     -H "Content-Type: application/json" \
     -d '{"initialBalance": 1000, "currency": "EUR"}'
```
*Copy the returned UUID (e.g., `c829...`).*

### 2. Subscribe to Real-time Updates (SSE)
Open a new terminal and watch for updates:
```bash
curl -N "http://localhost:8082/query/accounts/watch/{accountId}"
```

### 3. Credit the Account
```bash
curl -X PUT "http://localhost:8082/commands/account/credit" \
     -H "Content-Type: application/json" \
     -d '{"accountId": "{accountId}", "amount": 500, "currency": "EUR"}'
```

### 4. Debit the Account
```bash
curl -X PUT "http://localhost:8082/commands/account/debit" \
     -H "Content-Type: application/json" \
     -d '{"accountId": "{accountId}", "amount": 200, "currency": "EUR"}'
```

### 5. Query Account Details
```bash
curl "http://localhost:8082/query/accounts/byId/{accountId}"
```
