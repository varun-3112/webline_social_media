# Webline Social_Media practical assignment

> Created REST APIs using Spring Boot.

### Technology Stack
Component         | Technology
---               | ---
Backend (REST)    | [SpringBoot](https://projects.spring.io/spring-boot) (Java)
Security          | Token Based (Spring Security and [JWT](https://github.com/auth0/java-jwt))
REST Documentation| [Swagger UI / Springfox](https://github.com/springfox/springfox)
REST Spec         | [OpenAPI V3 Standard](https://www.openapis.org/) 
Database          | MySQL
Persistence       | JDBC driver
Server Build Tools| Maven(Java)

## Prerequisites

Ensure you have this installed before proceeding further
- Java 17

## About

This is a RESTful implementation of Users and Posts APIs, including Spring Security, Junit Testing, and Swagger.

### Features of the Project

* Users APIs:
  * [POST] Register User API (/api/auth/register)
  * [POST] Login API (/api/auth/login)
  * [GET] Get Users API (/api/allUsers) [Optional paging,Optional sorting,Data hiding based on user role]
  * [GET] Get Users by Ids API (/api/allUsersById)

* Posts APIs:
  * [POST] Create/Update Post API (/api/addPost) [Includes file upload]
  * [GET] Get Posts by User API  (/api/getPostsByUsers) [Optional filter for likes, dislikes, title, content, created_at, updated_at,Optional paging and sorting]
  * [POST] Like a post API (/api/like)
  * [POST] Dislike a post API (/api/dislike)
  * [GET] Get top 10/50 liked/disliked posts API (/api/getTopPosts) [Optional filter of user id]

* Backend
  * Token Based Security (using Spring security)
  * API documentation and Live Try-out links with Swagger 
  * MySQL Database
  * Using JDBC template to talk to relational database
    
## MySQL Database
* Database - social_media
* Tables
  * User
  * Post
  * Post_like_dislike_map

* Kindly refer file DataBase_Script_Information.docx which contain table creation query and images.

## Spring security
* JWT authentication is provided for all the APIs apart from following two.
  * Register User API
  * Login API

* Above two APIs will return JWT in response, which can be used in Bearer Authorization to access other APIs. Default validity is 5 minutes, which can be changed in application.properties.
* I have used HS256 algorithm to sign the JWT. The secret key is provided in the application.properties file.

### Build Backend (SpringBoot Java)
```bash
# Maven Build : Navigate to the root folder where pom.xml is present 
mvn clean install
```

### Start the API server
```bash
# Start the server (8095)
# port and other configurations for API servere is in [./src/main/resources/application.properties](/src/main/resources/application.properties) file

# If you build with maven jar location will be 
java -jar ./target/app-1.0.0.jar
```

### Accessing Application
Component         | URL                                      | Credentials
---               | ---                                      | ---
MySQL Database       |  jdbc:mysql://localhost:3306/social_media        |  User Name:`root`,password:`root`   
Swagger (API Ref) |  http://localhost:8095/swagger-ui/index.html   | 
Swagger Spec      |  http://localhost:8095/v3/api-docs          |



### Screenshots

#### Swagger UI APIs
![Dashboard](/screenshots/swagger_ui_api.png?raw=true)
---
#### Swagger UI Schemas
![Dashboard](/screenshots/swagger_ui_schema.png?raw=true)
---
#### Database Schema
![ER Diagram](/screenshots/db_schema.png?raw=true)
