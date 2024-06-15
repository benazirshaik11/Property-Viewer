# Property Viewer

Property Viewer is a web application for managing property data. This project uses Spring Boot with WebFlux, Thymeleaf for the view layer, R2DBC for reactive database access, and Spring Security for authentication.

## Table of Contents
- [Project Overview](#project-overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup Instructions](#setup-instructions)
- [Running the Application](#running-the-application)
- [Home page](#home-page)

## Project Overview

Property Viewer allows users to view, add, update, and delete properties. It supports bulk addition of properties and provides pagination and sorting functionality for the property listings.

## Features

- View a list of properties with pagination and sorting.
- Add, update, and delete individual properties.
- Bulk addition of properties.
- Authentication using Spring Security.
- Reactive programming model with Spring WebFlux.

## Technologies Used

- Java 21
- Spring Boot 3.3
- Spring WebFlux
- Spring Data R2DBC
- Thymeleaf
- Spring Security
- PostgreSQL
- Docker
- Gradle

## Setup Instructions


```bash
git clone https://github.com/benazirshaik11/Property-Viewer.git
cd property-viewer

```
### Running the Application

### Running as a spring-boot application

### Prerequisites

- JDK 21
- Gradle
- PostgreSQL

```bash
## please set JAVA_HOME
## make sure application.yml is pointing to the database
## make sure building table is created reference /init/init.sql

./gradlew clean build
java -jar "-Dspring.profiles.active=local" .\build\libs\propertyviewer-0.0.2-SNAPSHOT.jar
```

### Running as a docker container

### Prerequisites

- Docker
- docker-compose

```bash

## cmd
set SPRING_PROFILE=dev
## powerShell
$env:SPRING_PROFILE="dev"

docker-compose up --build




```
### Home page
- http://localhost:8080/buildings
