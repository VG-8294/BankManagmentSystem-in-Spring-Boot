# 🏦 SevaBank

SevaBank is a Spring Boot based Banking Management System that provides REST APIs for managing users, bank accounts, transactions, and administrative operations. The project follows a layered architecture and uses Spring Data JPA with PostgreSQL for persistent storage.

---

## 🚀 Features

### User Features
- Register a new user
- Login using Account Number, Email and Password
- Create Savings or Current Account
- Deposit money
- Withdraw money
- Check account balance
- Calculate interest for Savings Account
- View transaction history

### Admin Features
- Get all users
- Get users alphabetically
- Get users above a specific balance
- Get users below a specific balance
- Get users above a specific age
- Get users within a specific age range
- Search user by account number
- Search user by email
- Get users having Savings Account
- Get users having Current Account
- Get account having maximum balance
- Calculate total money in the bank
- Get total number of bank accounts
- Soft delete bank accounts

---

## 🛠 Tech Stack

- Java 11
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- REST APIs
- Postman

---

## 📂 Project Structure

```
src
├── controller
├── service
│   ├── interface
│   └── implementation
├── repository
├── entity
├── dto
├── enums
├── exception
├── configuration
└── util
```

---

## 🗄 Database

The project uses PostgreSQL with the following entities:

- User
- BankAccount
- Transaction
- Admin

Relationships:
- One User owns one Bank Account
- One Bank Account has multiple Transactions

---

## 📌 API Highlights

### User APIs

- Register User
- Login User
- Deposit Money
- Withdraw Money
- Check Balance
- Calculate Interest

### Admin APIs

- Get All Users
- Get Users by Email
- Get Users by Age
- Get Users by Balance
- Get Maximum Balance Account
- Total Money in Bank
- Total Accounts
- Soft Delete Account

---

## 🏗 Design Principles

The project follows:

- Layered Architecture
- Dependency Injection
- DTO Pattern
- Repository Pattern
- Constructor Injection
- Exception Handling
- Soft Delete Implementation
- RESTful API Design

---

## 📖 Concepts Used

- Spring Boot
- Spring Data JPA
- Hibernate ORM
- Entity Relationships
- One-to-One Mapping
- One-to-Many Mapping
- Optional
- Java Streams
- Lambda Expressions
- DTO Mapping
- Enum
- Custom Exceptions
- ResponseEntity
- HTTP Status Codes

---

## ▶️ Running the Project

1. Clone the repository

```bash
git clone <repository-url>
```

2. Configure PostgreSQL in `application.properties`

3. Create the database

```sql
CREATE DATABASE sevabank;
```

4. Run the application

```bash
mvn spring-boot:run
```

or

Run `SevaBankApplication.java` from your IDE.

---

## Future Improvements

- JWT Authentication
- Role Based Authorization
- Swagger/OpenAPI Documentation
- Pagination and Sorting
- Global Exception Handling
- Account Statements (PDF)
- Email Notifications
- Docker Support
- Unit & Integration Testing

---

## Author

**Vishal Gautam**