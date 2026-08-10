# AGENTS.md
## SevaBank — AI Coding Agent Instructions

## Purpose
SevaBank is a Spring Boot REST API that simulates a banking system. Follow these instructions when adding, modifying, or reviewing code in this repository.

## Tech Stack
- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- Lombok

Do not introduce another architecture such as hexagonal, CQRS, or microservices unless explicitly instructed.

## Project Structure
```text
controller/
service/
  impl/
repository/
entity/
dto/
  request/
  response/
mapper/
exception/
config/
enum/
```

## Architecture Rules
Use a strict one-way flow:

```text
Controller → Service Interface → Service Implementation → Repository → Database
```

Rules:
- Controllers must never access repositories directly.
- Controllers must never contain business logic.
- Business logic must live only in `ServiceImpl` classes.
- Repositories must only handle data access.
- Cross-service calls must go through service interfaces, not implementations or repositories.

## Controller Rules
Controllers should only:
- Validate requests with `@Valid @RequestBody`.
- Call the service layer.
- Return `ResponseEntity<ApiResponse<T>>`.

Do not add business logic or repository access in controllers. Avoid `try/catch` blocks unless there is a narrow, justified reason documented inline.

## DTO Rules
- Never expose JPA entities directly in REST endpoints.
- Always use request and response DTOs.
- Map entity ↔ DTO through dedicated mapper classes.
- Use Jakarta Bean Validation annotations on request DTOs.
- Annotate controller request parameters with `@Valid`.
- Do not expose password hashes, internal join IDs, or other internal-only fields in response DTOs.

## Entity Rules
- Use JPA annotations and Lombok where appropriate.
- Avoid `@Data` on entities with bidirectional relationships.
- Keep `@OneToMany` fetch type `LAZY` unless a specific query requires otherwise.
- Use defensive JSON annotations such as `@JsonIgnore`, `@JsonManagedReference`, or `@JsonBackReference` if needed.
- Generate `accountNumber` server-side at account creation.
- Do not use the database primary key as the externally visible account number.

Relationships:
- `User` → `OneToMany` → `BankAccount`
- `BankAccount` → `ManyToOne` → `User`
- `BankAccount` → `OneToMany` → `Transaction`

## Service Rules
Business logic belongs in the service layer, especially:
- Deposit
- Withdraw
- Transfer
- Login and authentication
- Account creation
- Balance validation
- Overdraft validation
- Interest calculation for savings accounts

Service interfaces define contracts. `ServiceImpl` classes implement the logic. Do not place business logic in interface default methods unless there is a strong shared-behavior reason.

## Transaction and Concurrency Rules
Money-moving operations require extra care.

- Any operation that reads and then writes a balance must be `@Transactional` at the service-implementation level.
- Use optimistic locking with a `@Version` field on `BankAccount` by default.
- Use pessimistic locking only when optimistic retries are not sufficient under contention.
- Transfer must be a single transactional unit: debit and credit must succeed or both roll back.
- Lock or update accounts in a consistent order to reduce deadlock risk.
- Keep transactional methods small and avoid external calls inside the transaction boundary.
- Consider idempotency keys for transfer and withdraw endpoints if the API is exposed beyond internal testing.

## Repository Rules
- Prefer Spring Data JPA derived queries when possible.
- Avoid loading all rows and filtering in Java if the database can do the work.
- Use `Optional<T>` for single-result lookups.
- Prefer locking strategies over plain `findById` for balance-affecting operations.

Common query examples:
- `findByEmail(...)`
- `findByUserId(...)`
- `findByAccountType(...)`
- `findByAccountNumber(...)`

## Account Rules
`BankAccount` contains:
- `accountNumber`
- `balance`
- `accountType`
- `interestRate`
- `overdraftLimit`

Business rules:
- Savings account: `interestRate` is required, `overdraftLimit` must be `null`.
- Current account: `overdraftLimit` is required, `interestRate` must be `null`.

Enforce these rules in the service layer during account creation, not only through database nullability.

## Database Rules
Tables:
- `users`
- `bank_account`
- `transactions`
- `admin`

Rules:
- Maintain relationships through foreign keys.
- Every money-affecting operation must create a matching row in `transactions` within the same transaction as the balance update.
- Do not commit secrets, credentials, or environment-specific database URLs to source control.
- Use `application.yml` profiles and environment variables for configuration.

## Exception Handling
Use custom exceptions such as:
- `UserNotFoundException`
- `InvalidCredentialsException`
- `DuplicateEmailException`
- `InsufficientBalanceException`
- `AccountNotFoundException`
- `InvalidAccountTypeException`

Handle exceptions centrally with `@RestControllerAdvice` in `GlobalExceptionHandler`. Do not catch exceptions in controllers unless there is a narrow, justified reason.

Exception to HTTP status mapping:
- `UserNotFoundException` / `AccountNotFoundException` → 404 Not Found
- `InvalidCredentialsException` → 401 Unauthorized
- `DuplicateEmailException` → 409 Conflict
- `InsufficientBalanceException` → 422 Unprocessable Entity
- Validation errors (`MethodArgumentNotValidException`) → 400 Bad Request
- Unexpected errors → 500 Internal Server Error

## API Response Contract
All endpoints must return a consistent response envelope.

Success:
```json
{
  "success": true,
  "data": { ... },
  "message": "Deposit successful",
  "timestamp": "2026-08-07T10:15:30Z"
}
```

Error:
```json
{
  "success": false,
  "data": null,
  "message": "Insufficient balance for this withdrawal",
  "errorCode": "INSUFFICIENT_BALANCE",
  "timestamp": "2026-08-07T10:15:30Z"
}
```

`GlobalExceptionHandler` is responsible for producing the error shape consistently.

## Naming Conventions
Classes:
- `UserService`
- `UserServiceImpl`
- `UserController`
- `UserRepository`
- `User`

DTOs:
- `CreateUserRequest`
- `UserResponse`

Do not use abbreviations like `ReqDto` or `ResDto`.

## Code Style
- Prefer constructor injection.
- Avoid field injection.
- Use `final` where possible.
- Keep methods short and single-purpose.
- Extract repeated logic into private helpers or dedicated utility classes.
- Prefer `Optional` over `null` when reading from repositories.
- Unwrap repository results with `.orElseThrow(...)` at the service boundary.

## Logging
- Use SLF4J (`@Slf4j`) instead of `System.out.println()`.
- Use `info` for normal business events.
- Use `warn` for expected failures such as validation rejections or insufficient balance.
- Use `error` for unexpected exceptions, failed transactions, and integration failures.
- Never log full account numbers, passwords, or other sensitive data.

## Testing Conventions
These rules apply once test infrastructure is added.

- Unit test service-implementation classes with mocked repositories.
- Integration test controllers with `@SpringBootTest` and Testcontainers for PostgreSQL.
- Add concurrency tests for every new balance-touching service method.

## Future Enhancements
The project may later include:
- Spring Security
- JWT authentication
- Swagger/OpenAPI
- Docker
- Unit testing
- Integration testing
- MapStruct
- Audit logging

Keep new code compatible with these additions. Prefer thin controllers, isolated mapper logic, and DTOs that already support validation annotations.

## Goal
Maintain clean architecture, SOLID principles, readable code, and production-style Spring Boot practices, with special care around money-moving operations.