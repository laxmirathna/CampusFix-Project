# CampusFix — Full-stack campus complaint management

CampusFix uses React + Vite, Spring Boot 3, Spring Security, JWT authentication, and MySQL. This package contains source code and setup instructions; dependencies are installed locally by npm/Maven.

## Features in this version
- Student registration and login; logout; JWT-based API authentication.
- BCrypt password hashing; no public admin registration.
- Server-side role enforcement: students see only their own complaints; administrators can see all complaints and manage status/assignment.
- Submit complaints with title, category, location, priority, description, and an optional image attachment (PNG/JPEG/WebP up to 1 MB).
- Search and filter by status; complaint tracking IDs; dashboard statistics.
- Admin can assign a staff name/email and update status (Submitted, Assigned, In Progress, Resolved, Rejected), with optional resolution note.
- MySQL persistence, environment-based configuration, configurable CORS, and Git ignore rules.

## Requirements
- Java 21
- Maven 3.9+
- Node.js 20+ and npm
- MySQL 8+

## 1. Configure local MySQL
Create a database and application user if you have not already done so. Run in MySQL as an administrator (replace the password with your own):

```sql
CREATE DATABASE IF NOT EXISTS campusfix;
CREATE USER IF NOT EXISTS 'campusfix_user'@'localhost' IDENTIFIED BY 'CHOOSE_A_STRONG_PASSWORD';
GRANT ALL PRIVILEGES ON campusfix.* TO 'campusfix_user'@'localhost';
FLUSH PRIVILEGES;
```

In the PowerShell terminal that will run the backend, set the environment variables (use your real local database password; do not put it in source files):

```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/campusfix?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true"
$env:DB_USERNAME="campusfix_user"
$env:DB_PASSWORD="YOUR_LOCAL_MYSQL_PASSWORD"
$env:APP_JWT_SECRET="replace-this-with-a-random-secret-at-least-32-characters-long"
$env:CORS_ALLOWED_ORIGINS="http://localhost:5173"
```

For an admin account, set these before the first backend startup. Use an email/password you control; the password must be at least 12 characters.

```powershell
$env:ADMIN_EMAIL="your-admin-email@example.com"
$env:ADMIN_PASSWORD="YOUR_STRONG_ADMIN_PASSWORD"
$env:ADMIN_NAME="CampusFix Administrator"
```

The configured admin account is created/promoted at startup. If you omit these variables, public registration creates **student** accounts only. There is no hard-coded default admin password.

## 2. Run Spring Boot
From the project root in a terminal with the variables above set:

```powershell
cd backend
mvn spring-boot:run
```

The backend listens on `http://localhost:8080`. Keep this terminal open.

## 3. Run React
Open a second terminal in the project root:

```powershell
cd frontend
npm install
npm run dev
```

Open the local URL Vite prints, normally `http://localhost:5173`. Register a student account to start.

## 4. Test the core workflow
1. Register a student account and sign in.
2. Submit a complaint and verify it appears in the student's list.
3. Sign out and sign in with the admin credentials configured above.
4. Verify the admin can see the complaint, assign a staff member, and update its status.
5. Sign back in as the student and verify the status change is visible.
6. Restart the backend and confirm records remain in MySQL.

## Deployment configuration
Set environment variables in the backend host; never commit secrets. Required values: `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `APP_JWT_SECRET` (32+ characters), and `CORS_ALLOWED_ORIGINS` (comma-separated exact frontend origins). For administrator bootstrap, set `ADMIN_EMAIL`, `ADMIN_PASSWORD` (12+ characters), and optionally `ADMIN_NAME`. Configure `VITE_API_URL` as a **frontend build-time** variable to the deployed backend base URL without `/api` (for example, `https://your-backend.example.com`).

A typical frontend host builds from the `frontend` directory with `npm run build` and publishes `dist`. A backend host builds from `backend` using `mvn clean package` and runs `java -jar target/backend-0.0.1-SNAPSHOT.jar` (or its platform's Maven build/run configuration). Confirm the current provider's pricing/free limits before deployment; do not enable paid services without approval.

## Security notes and limitations
- JWT tokens are stored in browser local storage in this starter implementation. For higher-risk production use, consider secure HttpOnly cookies and additional rate limiting.
- Admin bootstrap only promotes the configured email; changing `ADMIN_PASSWORD` after the account already exists does not reset its password. Use a controlled password reset process if needed.
- This version does not implement email verification, password reset emails, push/email notifications, or a staff-specific login/role.
- Do not commit `.env`, passwords, database URLs containing credentials, JWT secrets, or `node_modules`.
