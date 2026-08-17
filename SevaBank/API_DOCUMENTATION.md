# SevaBank REST API Documentation

**Project:** SevaBank
**API Version:** 1.0
**Base URL:** `http://localhost:8080`
**Content-Type:** `application/json`

---

## 📌 Overview

SevaBank is a Spring Boot based banking application that provides REST APIs for:

* User registration and authentication
* User profile management
* Bank account creation
* Deposits and withdrawals
* Balance checking
* Interest calculation
* Administrative operations
* User and account management

---

# 📚 API Modules

| Module       | Base Endpoint      | Description                                |
| ------------ | ------------------ | ------------------------------------------ |
| User         | `/api/user`        | Registration, login and profile management |
| Bank Account | `/api/bankAccount` | Banking and account operations             |
| Admin        | `/api/admin`       | Administrative operations                  |

---

# 🔹 Common Response Format

Most APIs return a `GenericDto<T>` response.

### Success Response

```json
{
  "status": "OK",
  "message": "Operation successful",
  "data": {}
}
```

### Error Response

```json
{
  "status": "BAD_REQUEST",
  "message": "Invalid request",
  "data": null
}
```

---

# 👤 User APIs

## 1. Register User

### `POST /api/user/register`

Registers a new user.

### Request Body

```json
{
  "name": "Vishal Gautam",
  "email": "vishal@gmail.com",
  "password": "password123",
  "age": 24
}
```

### Parameters

| Field      | Type    | Required | Description          |
| ---------- | ------- | -------- | -------------------- |
| `name`     | String  | Yes      | User's name          |
| `email`    | String  | Yes      | Unique email address |
| `password` | String  | Yes      | User password        |
| `age`      | Integer | Yes      | User's age           |

### Response

**201 CREATED**

```json
{
  "status": "CREATED",
  "message": "user registered",
  "data": {
    "id": 1,
    "name": "Vishal Gautam",
    "email": "vishal@gmail.com",
    "age": 24
  }
}
```

---

## 2. Login User

### `POST /api/user/login`

Authenticates an existing user.

### Request Body

```json
{
  "email": "vishal@gmail.com",
  "password": "password123"
}
```

### Response

**202 ACCEPTED**

```json
{
  "status": "ACCEPTED",
  "message": "Login successfull",
  "data": {}
}
```

---

## 3. Login V1

### `POST /api/user/v1/login`

Version 1 implementation of user login.

### Request Body

```json
{
  "email": "vishal@gmail.com",
  "password": "password123"
}
```

### Response

**202 ACCEPTED**

```json
{
  "status": "ACCEPTED",
  "message": "Login successfull",
  "data": {}
}
```

---

## 4. Update User

### `PUT /api/user/update/{id}`

Updates user information.

### Path Parameters

| Parameter | Type | Description |
| --------- | ---- | ----------- |
| `id`      | Long | User ID     |

### Example

```http
PUT /api/user/update/1
```

### Request Body

```json
{
  "name": "Vishal Gautam",
  "email": "vishal.new@gmail.com",
  "password": "newPassword",
  "age": 25
}
```

### Response

**202 ACCEPTED**

```json
{
  "status": "ACCEPTED",
  "message": "updated successfully",
  "data": {}
}
```

---

## 5. Partially Update User

### `PATCH /api/user/update/{id}`

Updates selected user details.

### Path Parameters

| Parameter | Type | Description |
| --------- | ---- | ----------- |
| `id`      | Long | User ID     |

### Request Body

Only the fields that need to be changed should be sent.

```json
{
  "age": 25
}
```

### Response

**202 ACCEPTED**

```json
{
  "status": "ACCEPTED",
  "message": "updated successfully",
  "data": {}
}
```

---

# 🏦 Bank Account APIs

## 6. Create Bank Account

### `POST /api/bankAccount`

Creates a new bank account for an existing user.

### Request Body

```json
{
  "userId": 1,
  "balance": 10000,
  "accountType": "SAVING",
  "interestRate": 4.5,
  "overdraftLimit": null
}
```

### Parameters

| Field            | Type   | Required | Description           |
| ---------------- | ------ | -------- | --------------------- |
| `userId`         | Long   | Yes      | Existing user ID      |
| `balance`        | Double | Yes      | Initial balance       |
| `accountType`    | Enum   | Yes      | `SAVING` or `CURRENT` |
| `interestRate`   | Double | Depends  | Interest rate         |
| `overdraftLimit` | Double | Depends  | Overdraft limit       |

### Response

**201 CREATED**

```json
{
  "status": "CREATED",
  "message": "Account created",
  "data": {}
}
```

---

## 7. Deposit Money

### `POST /api/bankAccount/deposit/{id}`

Deposits money into a bank account.

### Path Parameters

| Parameter | Type | Description     |
| --------- | ---- | --------------- |
| `id`      | Long | Bank account ID |

### Request Body

```json
{
  "balance": 5000
}
```

### Example

```http
POST /api/bankAccount/deposit/1001
```

### Response

**202 ACCEPTED**

```json
{
  "status": "ACCEPTED",
  "message": "Amount deposited!",
  "data": {}
}
```

---

## 8. Withdraw Money

### `POST /api/bankAccount/withdraw/{id}`

Withdraws money from a bank account.

### Request Body

```json
{
  "balance": 2000
}
```

### Example

```http
POST /api/bankAccount/withdraw/1001
```

### Response

**202 ACCEPTED**

```json
{
  "status": "ACCEPTED",
  "message": "Amount withdrawn",
  "data": {}
}
```

---

## 9. Check Balance

### `GET /api/bankAccount/balance/{id}`

Returns the current balance of a bank account.

### Example

```http
GET /api/bankAccount/balance/1001
```

### Response

**202 ACCEPTED**

```json
{
  "status": "ACCEPTED",
  "message": "",
  "data": {
    "balance": 13000
  }
}
```

---

## 10. Calculate Interest

### `GET /api/bankAccount/interest/{id}`

Calculates interest for a bank account.

### Example

```http
GET /api/bankAccount/interest/1001
```

### Success Response

**202 ACCEPTED**

```json
{
  "status": "ACCEPTED",
  "message": "",
  "data": {}
}
```

### Account Not Found

**404 NOT FOUND**

```json
{
  "status": "NOT_FOUND",
  "message": "account not found"
}
```

---

# 👨‍💼 Admin APIs

Base URL:

```text
/api/admin
```

---

## 11. Get All Users

### `GET /api/admin/getAllUsers`

Returns all registered users.

### Response

**200 OK**

```json
{
  "status": "OK",
  "message": "All the users are:",
  "data": []
}
```

---

## 12. Get Users Below Balance

### `GET /api/admin/getUsersLessThanBal/{amount}`

Returns users whose balance is less than the specified amount.

### Example

```http
GET /api/admin/getUsersLessThanBal/5000
```

### Path Parameter

| Parameter | Type   | Description       |
| --------- | ------ | ----------------- |
| `amount`  | Double | Balance threshold |

---

## 13. Get Users Below Balance — V1

### `GET /api/admin/v1/getUsersThanBal/{amount}`

Version 1 implementation of the balance query.

---

## 14. Get Users Having Saving Account

### `GET /api/admin/getUsersHavingSaving`

Returns users who have a `SAVING` account.

---

## 15. Get Users Having Current Account

### `GET /api/admin/getUsersHavingCurrent`

Returns users who have a `CURRENT` account.

---

## 16. Get Old Age Users

### `GET /api/admin/getOldAgeUsers`

Returns users matching the application's old-age criteria.

---

## 17. Get Old Age Users — V1

### `GET /api/admin/v1/getOldAgeUsers`

Version 1 implementation.

---

## 18. Get User By Email

### `GET /api/admin/getUsersByEmail/{email}`

Returns a user using their email address.

### Example

```http
GET /api/admin/getUsersByEmail/vishal@gmail.com
```

### Path Parameter

| Parameter | Type   | Description |
| --------- | ------ | ----------- |
| `email`   | String | User email  |

---

## 19. Get All User Emails

### `GET /api/admin/getAllUsersEmail`

Returns all registered user email addresses.

### Response

```json
[
  "vishal@gmail.com",
  "rahul@gmail.com",
  "amit@gmail.com"
]
```

> This endpoint currently returns `List<String>` directly instead of `GenericDto`.

---

## 20. Get Total Number of Accounts

### `GET /api/admin/getTotalNoAcc`

Returns the total number of bank accounts.

### Response

```json
{
  "status": "OK",
  "message": "The total number of accounts in bank are:",
  "data": 25
}
```

---

## 21. Get Total Number of Accounts — V1

### `GET /api/admin/v1/getTotalNoAcc`

Version 1 implementation.

---

## 22. Get Total Money in Bank

### `GET /api/admin/getTotalMoney`

Returns the total amount of money across all bank accounts.

### Response

```json
{
  "status": "OK",
  "message": "The total in money in bank is:",
  "data": 2500000.0
}
```

---

## 23. Get User With Maximum Balance

### `GET /api/admin/getUserWithMaxBal`

Returns the user associated with the account having the maximum balance.

---

## 24. Get Users Above Specific Balance

### `GET /api/admin/getUsersOverCertainBal/{amt}`

Returns users whose balance is greater than the specified amount.

### Example

```http
GET /api/admin/getUsersOverCertainBal/50000
```

### Path Parameter

| Parameter | Type   | Description     |
| --------- | ------ | --------------- |
| `amt`     | Double | Minimum balance |

---

## 25. Get Users Above Specific Balance — V1

### `GET /api/admin/v1/getUsersOverCertainBal/{amt}`

Version 1 implementation.

---

## 26. Get Users Above Specific Age

### `GET /api/admin/getUsersAboveSomeAge/{age}`

Returns users whose age is greater than the specified age.

### Example

```http
GET /api/admin/getUsersAboveSomeAge/60
```

### Path Parameter

| Parameter | Type    | Description   |
| --------- | ------- | ------------- |
| `age`     | Integer | Age threshold |

---

## 27. Get Users Above Specific Age — V1

### `GET /api/admin/v1/getUsersAboveSomeAge/{age}`

Version 1 implementation.

---

## 28. Get User By Account Number

### `GET /api/admin/getUserByAccNo/{accNo}`

Returns the user associated with a bank account.

### Example

```http
GET /api/admin/getUserByAccNo/1001
```

### Path Parameter

| Parameter | Type | Description         |
| --------- | ---- | ------------------- |
| `accNo`   | Long | Bank account number |

---

## 29. Get User By Account Number — V1

### `GET /api/admin/v1/getUserByAccNo/{accNo}`

Version 1 implementation.

---

## 30. Get Users Between Ages

### `GET /api/admin/getUsersBwAge`

Returns users within a specified age range.

### Current Request Body

```json
{
  "minAge": 20,
  "maxAge": 30
}
```

### Recommended API

Instead of sending a request body with a GET request, use query parameters:

```http
GET /api/admin/getUsersBwAge?minAge=20&maxAge=30
```

---

## 31. Get All Bank Accounts

### `GET /api/admin/getAllBankAccounts`

Returns all bank accounts.

### Response

```json
[
  {
    "accNo": 1001,
    "balance": 10000,
    "accountType": "SAVING",
    "interestRate": 4.5
  }
]
```

> This endpoint currently returns `List<BankAccountResponseDto>` directly instead of `GenericDto`.

---

## 32. Delete Bank Account

### `DELETE /api/admin/deleteAccount/{id}`

Deletes a bank account by ID.

### Example

```http
DELETE /api/admin/deleteAccount/1001
```

### Path Parameter

| Parameter | Type | Description     |
| --------- | ---- | --------------- |
| `id`      | Long | Bank account ID |

### Response

**200 OK**

```json
{
  "status": "OK",
  "message": "Account deleted!"
}
```

---

# 📊 Complete API Reference

|  # | Method   | Endpoint                                     | Description            |
| -: | -------- | -------------------------------------------- | ---------------------- |
|  1 | `POST`   | `/api/user/register`                         | Register user          |
|  2 | `POST`   | `/api/user/login`                            | Login                  |
|  3 | `POST`   | `/api/user/v1/login`                         | Login V1               |
|  4 | `PUT`    | `/api/user/update/{id}`                      | Update user            |
|  5 | `PATCH`  | `/api/user/update/{id}`                      | Partial update         |
|  6 | `POST`   | `/api/bankAccount`                           | Create account         |
|  7 | `POST`   | `/api/bankAccount/deposit/{id}`              | Deposit money          |
|  8 | `POST`   | `/api/bankAccount/withdraw/{id}`             | Withdraw money         |
|  9 | `GET`    | `/api/bankAccount/balance/{id}`              | Check balance          |
| 10 | `GET`    | `/api/bankAccount/interest/{id}`             | Calculate interest     |
| 11 | `GET`    | `/api/admin/getAllUsers`                     | Get all users          |
| 12 | `GET`    | `/api/admin/getUsersLessThanBal/{amount}`    | Users below balance    |
| 13 | `GET`    | `/api/admin/v1/getUsersThanBal/{amount}`     | Users below balance V1 |
| 14 | `GET`    | `/api/admin/getUsersHavingSaving`            | Saving account users   |
| 15 | `GET`    | `/api/admin/getUsersHavingCurrent`           | Current account users  |
| 16 | `GET`    | `/api/admin/getOldAgeUsers`                  | Old-age users          |
| 17 | `GET`    | `/api/admin/v1/getOldAgeUsers`               | Old-age users V1       |
| 18 | `GET`    | `/api/admin/getUsersByEmail/{email}`         | Find user by email     |
| 19 | `GET`    | `/api/admin/getAllUsersEmail`                | Get all emails         |
| 20 | `GET`    | `/api/admin/getTotalNoAcc`                   | Total accounts         |
| 21 | `GET`    | `/api/admin/v1/getTotalNoAcc`                | Total accounts V1      |
| 22 | `GET`    | `/api/admin/getTotalMoney`                   | Total money            |
| 23 | `GET`    | `/api/admin/getUserWithMaxBal`               | Maximum balance user   |
| 24 | `GET`    | `/api/admin/getUsersOverCertainBal/{amt}`    | Users above balance    |
| 25 | `GET`    | `/api/admin/v1/getUsersOverCertainBal/{amt}` | Above balance V1       |
| 26 | `GET`    | `/api/admin/getUsersAboveSomeAge/{age}`      | Users above age        |
| 27 | `GET`    | `/api/admin/v1/getUsersAboveSomeAge/{age}`   | Above age V1           |
| 28 | `GET`    | `/api/admin/getUserByAccNo/{accNo}`          | User by account        |
| 29 | `GET`    | `/api/admin/v1/getUserByAccNo/{accNo}`       | User by account V1     |
| 30 | `GET`    | `/api/admin/getUsersBwAge`                   | Users between ages     |
| 31 | `GET`    | `/api/admin/getAllBankAccounts`              | All bank accounts      |
| 32 | `DELETE` | `/api/admin/deleteAccount/{id}`              | Delete account         |

---

# ⚠️ API Design Notes

### HTTP Status Codes

Your current implementation uses `202 ACCEPTED` for several synchronous operations.

For a production REST API, these would generally be better as:

| Operation          | Current | Recommended |
| ------------------ | ------: | ----------: |
| Register           |     201 |       ✅ 201 |
| Create Account     |     201 |       ✅ 201 |
| Login              |     202 |     **200** |
| Update User        |     202 |     **200** |
| Deposit            |     202 |     **200** |
| Withdraw           |     202 |     **200** |
| Check Balance      |     202 |     **200** |
| Calculate Interest |     202 |     **200** |
| Delete Account     |     200 |       ✅ 200 |
| Not Found          |     404 |       ✅ 404 |

### Other Recommendations

* Do not return passwords in `UserResponseDto`.
* Use query parameters for `getUsersBwAge`.
* Consider consistent `GenericDto<T>` responses.
* Consider REST-style resource naming as the project matures.
* Consider moving versioning to `/api/v1/...` instead of individual `/v1` endpoints.

---

## 🛠️ Technologies

* Java
* Spring Boot
* Spring Web
* Spring Data JPA
* Hibernate
* PostgreSQL
* REST API
* Maven

---

## 👨‍💻 Author

**Vishal Gautam**

> SevaBank — Banking Management REST API built using Spring Boot.
