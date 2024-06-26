### SolarWatch
SolarWatch, a Spring Boot Web API project, provides sunrise and sunset times for any city on a specified date.

## Overview

SolarWatch utilizes the Spring Boot framework for the backend to handle API requests and PostgreSQL for data storage. It offers authentication using JSON Web Tokens (JWT) along with Spring Security. The frontend is built with React.js, providing a user-friendly interface for interacting with the API.

## Features
Sunrise and Sunset Times: Retrieve sunrise and sunset times for any city on a specified date.
User Authentication: Secure API endpoints with user authentication using JWT and Spring Security.
Database Integration: Store and manage data using PostgreSQL, ensuring data consistency and reliability.
Docker Server: Utilize Docker for containerization, enabling easy deployment and scalability.

## Technologies
- Frontend: React.js
- Backend: JAVA Spring Boot
- Database: PostgreSQL
- Authentication: JSON Web Tokens (JWT) + Spring Security
- Docker server

## Installation

1. Clone the repository
    git clone <repository-url>

2. Navigate to the project directory:
    cd solarwatch
   
3. Build the frontend:
    cd client
    npm install
    npm run build

4. Build the backend:
    cd ..
    cd server
    mvn clean package

5. Set up environment variables:
    - Configure the database connection details in the application.properties file.
    - Set up JWT secret key and other necessary configurations.

6. Run the Docker container:
    docker-compose up --build

7. Access the SolarWatch API at http://localhost:8080.

## Usage
- API Endpoints: Interact with the SolarWatch API to retrieve sunrise and sunset times by sending HTTP requests to the provided endpoints.
- Authentication: Obtain a JWT token by authenticating with valid credentials, then include the token in subsequent requests to access protected endpoints.
