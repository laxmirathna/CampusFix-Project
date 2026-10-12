# CampusFix – College Issue Reporting System

## Overview

CampusFix is a full-stack web application designed to simplify the process of reporting, tracking, and managing issues within a college campus. It provides a centralized platform where students can report problems related to campus facilities and services, while administrators can monitor complaints and update their status.

The system improves communication between students and college administration by providing a structured and transparent complaint management process.

## Features

### Student Module
- Student registration and login
- Submit campus-related complaints
- View submitted complaints
- Track complaint status
- Access complaint information through a dashboard

### Admin Module
- Administrator login
- View and manage reported complaints
- Update complaint status
- Monitor reported campus issues

### General Features
- Full-stack web application
- Database-backed complaint storage
- REST API integration
- Responsive web interface
- Cloud deployment architecture

## Technology Stack

| Component | Technology |
|---|---|
| Frontend | React.js |
| Build Tool | Vite |
| Backend | Java, Spring Boot |
| Database | MySQL |
| API | REST API |
| Frontend Deployment | Vercel |
| Backend Deployment | Render |
| Database Hosting | Aiven |

## System Architecture

The application follows a three-layer architecture:

1. **Frontend:** React.js provides the user interface for students and administrators.
2. **Backend:** Spring Boot handles authentication, complaint management, business logic, and API requests.
3. **Database:** MySQL stores user and complaint information.

The frontend communicates with the backend through REST APIs, and the backend interacts with the MySQL database to store and retrieve information.
## Workflow Diagram

The following diagram illustrates the complete workflow of CampusFix, from student complaint submission to administrator review and status tracking.

![Uploading CampusFix College Issue Workflow.png…]()

## Application Workflow

1. A student registers or logs in to the application.
2. The student submits a complaint describing a campus issue.
3. The backend processes the request and stores the complaint in the database.
4. The administrator logs in and views submitted complaints.
5. The administrator updates the complaint status as the issue progresses.
6. The student checks the dashboard to view the latest complaint status.

## Live Demo

- **Frontend:** https://campus-fix-project-frontend.vercel.app
- **Backend:** https://campusfix-project-smfl.onrender.com

Note: The backend URL is the service endpoint and may not display a web page when opened directly. The application requires the frontend and backend to be correctly configured to communicate with each other.

## Repository Structure

```text
CampusFix-Project/
├── frontend/
│   ├── src/
│   ├── public/
│   ├── package.json
│   └── ...
├── backend/
│   ├── src/
│   ├── pom.xml
│   ├── Dockerfile
│   └── ...
└── README.md
```

## Local Setup

### Prerequisites

Install the following software:

- Node.js and npm
- Java Development Kit (JDK 21)
- Maven
- MySQL
- Git

### 1. Clone the Repository

```bash
git clone https://github.com/laxmirathna/CampusFix-Project.git
cd CampusFix-Project
```

### 2. Configure the Database and Backend

Open the `backend` directory and configure the required environment variables for the MySQL database and administrator account.

Required configuration includes:

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `ADMIN_EMAIL`
- `ADMIN_PASSWORD`
- `APP_JWT_SECRET`
- `CORS_ALLOWED_ORIGINS`

Use your own local database credentials and secure application secrets. Do not commit passwords, tokens, or `.env` files to GitHub.

Start the backend using Maven:

```bash
cd backend
mvn spring-boot:run
```

### 3. Configure and Start the Frontend

Open another terminal:

```bash
cd frontend
npm install
```

Create or configure the frontend environment variable `VITE_API_URL` to point to the backend API base URL. For local development, use the correct local backend address and port.

Start the frontend:

```bash
npm run dev
```

Open the local URL displayed in the terminal, usually `http://localhost:5173`.

## Future Enhancements

- Email notifications for complaint updates
- Complaint priority and category filters
- Image attachments for reported issues
- Complaint analytics and reporting
- Role-based access control
- Improved mobile experience

## Project Objective

The primary objective of CampusFix is to digitize the campus complaint management process, reduce communication delays, and help college administrators track and resolve reported issues more efficiently.

## License

This project is developed for academic and educational purposes. Add a specific open-source license if you intend to distribute it under particular reuse terms.

## Author

**laxmi rathna**

GitHub: https://github.com/laxmirathna

