# E-Wallet Backend Service

A simple REST API wallet service built with **Spring Boot** and **MySQL**.  
Supports:  
- Wallet creation  
- Credit/Debit transactions  
- Atomic transfer between wallets  
- Idempotency via stored key  
- Wallet balance query  

---

## Requirements
- Java 17  
- Maven   
- MySQL database  
- Optional: Postman / curl for testing  


## Configuration
Update src/main/resources/application.yml or application.properties with your credentials:
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/e_wallet_db
    username: root
    password: Password
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

## Endpoints
Create Wallet
POST /wallets
Content-Type: application/json
Request body: 
{
  "initialBalance": 10000
}
Response:
{
  "id": 1,
  "balance": 10000
}

Get Wallet Details
GET /wallets/{id}
Response:
{
  "id": 1,
  "balance": 10000
}

Credit/Debit Wallet
{
  "walletId": 1,
  "amount": 5000,
  "idempotencyKey": "ab123"
}
Response
{
  "id": 1,
  "walletId": 1,
  "amount": 5000,
  "idempotencyKey": "ab123"
}

Transfer Between Wallets
POST /api/9ja/settlement/transfer
Content-Type: application/json

Request body:
{
  "sourceWalletId": 1,
  "destinationWalletId": 2,
  "amount": 2000,
  "idempotencyKey": "transfer001"
}


