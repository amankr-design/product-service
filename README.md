# 🛒 Product Service — E-Commerce Backend

A production-ready RESTful microservice for e-commerce product management, built with Spring Boot 3. Features JWT authentication, Redis caching, JPA/Hibernate persistence, Docker containerization, and a full test suite.

---

## 📌 Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [API Endpoints](#api-endpoints)
- [Getting Started](#getting-started)
- [Running with Docker](#running-with-docker)
- [Running Tests](#running-tests)
- [Environment Profiles](#environment-profiles)

---

## ✅ Features

- JWT-based authentication (register, login, secured endpoints)
- Full CRUD for products with role-based access
- Redis caching on product reads for performance
- Global exception handling with meaningful error responses
- Request/response logging via AOP
- Swagger/OpenAPI documentation
- Spring Profiles for local, docker environments
- Comprehensive unit and integration tests
- Fully Dockerized with Docker Compose

---

## 🛠 Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.5 |
| Security | Spring Security + JWT |
| Database | MySQL 8.0 |
| ORM | Spring Data JPA / Hibernate |
| Caching | Redis 7.0 |
| API Docs | SpringDoc OpenAPI (Swagger UI) |
| Testing | JUnit 5, Mockito, MockMvc, H2 |
| Build | Maven |
| Containerization | Docker, Docker Compose |

---

## 🏗 Architecture

```
┌─────────────────────────────────────────────────────┐
│                   Client (Postman / Browser)         │
└─────────────────────┬───────────────────────────────┘
                      │ HTTP Request
                      ▼
┌─────────────────────────────────────────────────────┐
│              Spring Security Filter Chain            │
│         (JWT Authentication Filter)                  │
└─────────────────────┬───────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────┐
│                  Controller Layer                    │
│        AuthController | ProductController            │
└──────────┬──────────────────────────┬───────────────┘
           │                          │
           ▼                          ▼
┌──────────────────┐      ┌──────────────────────────┐
│   Auth Service   │      │     Product Service       │
│  (JWT + Users)   │      │  (@Cacheable via Redis)   │
└──────────┬───────┘      └────────────┬─────────────┘
           │                           │
           ▼                           ▼
┌──────────────────┐      ┌────────────────────────────┐
│  User Repository │      │    Product Repository      │
│    (MySQL)       │      │       (MySQL)              │
└──────────────────┘      └────────────────────────────┘
                                       │
                          ┌────────────▼───────────────┐
                          │       Redis Cache           │
                          │  (products cached by id)   │
                          └────────────────────────────┘
```

---

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/aman/ecommerce/productservice/
│   │   ├── aspect/          # AOP logging
│   │   ├── auth/            # JWT service
│   │   ├── config/          # Security, ModelMapper, OpenAPI config
│   │   ├── controller/      # REST controllers
│   │   ├── dto/             # Request/Response DTOs
│   │   ├── entity/          # JPA entities
│   │   ├── enums/           # Role enum
│   │   ├── exception/       # Custom exceptions + global handler
│   │   ├── repository/      # Spring Data JPA repositories
│   │   ├── security/        # JWT filter, entry points
│   │   ├── service/         # Business logic (interfaces + impl)
│   │   └── user/            # User entity
│   └── resources/
│       ├── application.yaml           # Base config (activates local profile)
│       ├── application-local.yaml     # Local development config
│       └── application-docker.yaml    # Docker/production config
└── test/
    ├── java/
    │   └── controller/      # ProductControllerTest (MockMvc)
    │   └── service/         # ProductServiceImplTest (Mockito)
    └── resources/
        └── application.yml  # Test config (H2 in-memory, Redis disabled)
```

---

## 📡 API Endpoints

### Auth Endpoints (Public)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Login and get JWT token |

### Product Endpoints (Protected — requires JWT)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/products` | Create a new product |
| GET | `/api/products` | Get all products |
| GET | `/api/products/{id}` | Get product by ID (cached in Redis) |
| PUT | `/api/products/{id}` | Update a product (evicts cache) |
| DELETE | `/api/products/{id}` | Delete a product (evicts cache) |

### Swagger UI

Once the app is running, visit:
```
http://localhost:8081/swagger-ui/index.html
```

---

## 🚀 Getting Started

### Prerequisites

- Java 21
- Maven 3.9+
- MySQL 8.0 (for local profile)
- Redis (for local profile)
- Docker + Docker Compose (for Docker profile)

### Local Setup

**1. Clone the repository**
```bash
git clone https://github.com/amankr-design/product-service.git
cd product-service
```

**2. Create MySQL database and user**
```sql
CREATE DATABASE ecommerce_product_db;
CREATE USER 'springuser'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON ecommerce_product_db.* TO 'springuser'@'localhost';
```

**3. Update `application-local.yaml`** with your MySQL credentials.

**4. Start Redis locally**
```bash
# On Windows (via WSL or Docker)
docker run -d -p 6379:6379 redis:7.0

# On Mac/Linux
redis-server
```

**5. Build and run**
```bash
mvn clean install
mvn spring-boot:run
```

The app will start on `http://localhost:8081`

---

## 🐳 Running with Docker

Everything runs with a single command — MySQL, Redis, and the app together.

**1. Build the jar**
```bash
mvn clean package -DskipTests
```

**2. Build the Docker image**
```bash
docker build -t product-service:1.0 .
```

**3. Start all services**
```bash
docker compose up
```

This starts:
- MySQL on port `3306`
- Redis on port `6379`
- Product Service on port `8081`

**Access the app:**
```
http://localhost:8081/swagger-ui/index.html
```

---

## 🧪 Running Tests

Tests use H2 in-memory database and Redis is disabled — no external services needed.

```bash
mvn test
```

**Test coverage:**

| Test Class | Type | Tests |
|---|---|---|
| `ProductControllerTest` | Integration (MockMvc) | 5 |
| `ProductServiceImplTest` | Unit (Mockito) | 7 |
| `ProductServiceApplicationTests` | Context load | 1 |
| **Total** | | **13** |

---

## ⚙️ Environment Profiles

| Profile | Activated by | Database | Redis |
|---|---|---|---|
| `local` | Default | MySQL (localhost) | Redis (localhost) |
| `docker` | `SPRING_PROFILES_ACTIVE=docker` | MySQL (docker service) | Redis (docker service) |
| `test` | Test runner | H2 in-memory | Disabled |

---

## 🔐 How Authentication Works

1. Register a user via `POST /api/auth/register`
2. Login via `POST /api/auth/login` — receive a JWT token
3. Add the token to all product requests:
   ```
   Authorization: Bearer <your_jwt_token>
   ```

---

## 📬 Sample Requests

**Register**
```json
POST /api/auth/register
{
  "username": "aman",
  "email": "aman@example.com",
  "password": "password123"
}
```

**Login**
```json
POST /api/auth/login
{
  "username": "aman",
  "password": "password123"
}
```

**Create Product**
```json
POST /api/products
Authorization: Bearer <token>

{
  "name": "iPhone 16",
  "description": "Apple Mobile",
  "price": 80000.0,
  "quantity": 10
}
```

---

*Built by Aman Kumar*
