# Library Management System

A Spring MVC + Thymeleaf web app for managing a library: books, members, book
orders (issuing), returns, and students.

## Tech Stack

- Java 17
- Spring Boot 2.7.5
- Spring MVC, Spring Data JPA, Hibernate
- Thymeleaf, Bootstrap 5
- MySQL
- Maven

## Modules

| Module  | Routes                                   |
|---------|-------------------------------------------|
| Books   | `/books`, `/books/new`, `/books/edit/{id}` |
| Members | `/members`, `/members/new`, `/members/edit/{id}` |
| Orders  | `/orders`, `/orders/new`                   |
| Returns | `/returns`, `/returns/new`                 |
| Students| `/students`, `/students/new`, `/students/edit/{id}` |

## Project Structure

```
src/main/java/net/javaguides/studentmanagement/
├── StudentManagementSystemApplication.java
├── controller/    (Book, BookOrder, BookReturn, Member, Student, Home)
├── model/         (Book, BookOrder, BookReturn, Member, Student)
├── repository/    (Spring Data JPA repositories)
└── service/
    └── impl/

src/main/resources/
├── application.properties   (env-var driven, see below)
└── templates/                (Thymeleaf views)
```

## Running locally (without Docker)

1. Create a MySQL database:
   ```sql
   CREATE DATABASE library_db;
   ```
2. Set environment variables (or edit `application.properties` defaults directly):
   ```
   SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/library_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
   SPRING_DATASOURCE_USERNAME=root
   SPRING_DATASOURCE_PASSWORD=your_password
   ```
3. Run it:
   ```
   ./mvnw spring-boot:run
   ```
4. Open `http://localhost:8080/` — Hibernate auto-creates the tables (`books`,
   `members`, `book_orders`, `book_returns`, `students`).

## Running with Docker Compose (app + MySQL together)

```
docker compose up --build
```

Then open `http://localhost:8080/`. This spins up a MySQL 8 container and
wires the app to it automatically — no local MySQL install needed.

## Deploying to Render

The included `Dockerfile` builds a slim runtime image and reads the port
Render injects at runtime via the `PORT` env var (already wired into
`application.properties` as `server.port=${PORT:8080}`).

**Render does not offer a managed MySQL database** (only Postgres/Redis), so
you'll need an external MySQL host — free options include
[PlanetScale](https://planetscale.com), [Aiven](https://aiven.io), or
[Railway](https://railway.app).

Steps:

1. Push this repo to GitHub (see below).
2. In the Render dashboard: **New → Web Service → connect this repo**.
   Render will detect the `Dockerfile` automatically (or use the included
   `render.yaml` Blueprint via **New → Blueprint**).
3. Under **Environment**, add:
   - `SPRING_DATASOURCE_URL` — e.g. `jdbc:mysql://<host>:3306/<db>?useSSL=true&serverTimezone=UTC`
   - `SPRING_DATASOURCE_USERNAME`
   - `SPRING_DATASOURCE_PASSWORD`
4. Deploy. Render builds the Docker image and starts the container; the app
   will be live at the `.onrender.com` URL Render gives you.

## Pushing this project to GitHub

```bash
cd library-management-system
git init
git add .
git commit -m "Initial commit: Library Management System"
git branch -M main
git remote add origin https://github.com/<your-username>/<your-repo>.git
git push -u origin main
```

## Known gaps

- `students.html`/`update_student.html` exist, but there's no delete
  confirmation dialog on the students list (unlike books/members).
- No authentication/authorization — anyone with the URL can add/edit/delete
  records. Add Spring Security before exposing this publicly with real data.
- `spring.jpa.hibernate.ddl-auto=update` is convenient for demos but isn't
  recommended for production; use migrations (Flyway/Liquibase) for a real
  deployment.

## Credits

Adapted from the original Employee/Student Management CRUD structure
(originally by Ramesh Fadatare) and extended into a full Library Management
System.
