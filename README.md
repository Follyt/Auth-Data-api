# Auth-Data API

A two-service Spring Boot application with JWT authentication and text transformation.  
Services:

- **auth-api (A)** — handles registration/login, JWT authentication, user and log storage.  
- **data-api (B)** — processes text (e.g., converts it to uppercase).  
- **PostgreSQL** — persistent storage.

---

## 📦 Architecture
client → auth-api → data-api
            |
        PostgreSQL

- Client authenticates via `auth-api` (`/api/auth/login` or `/register`) and receives a JWT in /api/auth/login.  
- Client sends a request to `/api/process` on `auth-api`, which forwards it to `data-api` with an `X-Internal-Token`.  
- `data-api` returns the transformed text, while `auth-api` stores the log in `processing_log`.

---

## 🚀 Run with Docker Compose
1. Clone the repository:
     ```bash
     git clone https://github.com/Follyt/Auth-Data-api.git
     cd Auth-Data-api
   
2. Start containers:
     docker compose up --build -d

3. Check status:
     docker compose ps
You should see something like that:
<img width="1762" height="119" alt="image" src="https://github.com/user-attachments/assets/3a50c5bc-7c42-40ba-8b30-b192a66b0c17" />

If you see only Postgres - do this inside of Auth-Data-api:
docker compose up -d auth-api data-api

---

## 🧪 Example requests (Postman)

1. Register:
POST: http://localhost:8080/api/auth/register
Body: raw {
        "email": "v1@post.com",
        "password_hash": "1234567890"
      }

2. Login:
Post: http://localhost:8080/api/auth/login
Body: raw {
        "email": "v1@post.com",
        "password_hash": "1234567890"
      }
Here, after login, you`ll get your JWT.

3. Process:
Authorization: Bearer Token (you already get it when u logged in)
POST: http://localhost:8080/api/process
Headers: 
1) Key: Content-Type; Value: application/json;
2) Key: X-Internal-Token; Value: SUPER_SECRET_93e6e2a1c4 (u can change it if you want, but also need to change it in files)
Body: raw { "text": "hello world" }

---

## 🔑 Environment variables  
- INTERNAL_TOKEN=SUPER_SECRET_93e6e2a1c4  
- JWT_SECRET=YOUR_JWT_SECRET 

---

## ✅ Requirements

Docker & Docker Compose
Java services buildable with mvn package
Ports 8080, 8081, and 15432 must be available
