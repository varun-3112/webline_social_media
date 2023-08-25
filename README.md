# Webline Social_Media practical assignment

> Created REST APIs using Spring Boot.

### Technology Stack
Component         | Technology
---               | ---
Backend (REST)    | [SpringBoot](https://projects.spring.io/spring-boot) (Java)
Security          | Token Based (Spring Security and [JWT](https://github.com/auth0/java-jwt) )
REST Documentation| [Swagger UI / Springfox](https://github.com/springfox/springfox)
REST Spec         | [Open API Standard](https://www.openapis.org/) 
Database          | MySQL
Persistence       | JDBC template
Server Build Tools| Maven(Java)

## Folder Structure
```bash
PROJECT_FOLDER
│  README.md
│  pom.xml           
│  build.gradle
└──[src]      
│  └──[main]      
│     └──[java]      
│     └──[resources]
│        │  application.properties #contains springboot cofigurations
│        │  schema.sql  # Contains DB Script to create tables that executes during the App Startup          
│        │  data.sql    # Contains DB Script to Insert data that executes during the App Startup (after schema.sql)
│        └──[public]    # keep all html,css etc, resources that needs to be exposed to user without security
│
└──[target]              #Java build files, auto-created after running java build: mvn install
│  └──[classes]
│     └──[public]
│     └──[webui]         #webui folder is created by (maven/gradle) which copies webui/dist folder 
│                        #the application.properties file list webui as a resource folder that means files can be accesses http://localhost/<files_inside_webui> 
│
└──[webui]
   │  package.json     
   │  angular-cli.json   #ng build configurations)
   └──[node_modules]
   └──[src]              #frontend source files
   └──[dist]             #frontend build files, auto-created after running angular build: ng -build
```

## Prerequisites
Ensure you have this installed before proceeding further
- Java 17

## About

This is an RESTfull implementation of Users and Posts APIs which include Spring Security, Junit Testing, and Swagger.

### Features of the Project
* Users APIs:
  * Register User API
  * Login API
  * Get Users API [Optional paging,Optional sorting,Data hiding based on user role]
  * Get Users by Ids API

* Posts APIs:
  * Create/Update Post API [Includes file upload]
  * Get Posts by User API  [Optional filter for likes, dislikes, title, content, created_at, updated_at,Optional paging and sorting]
  * Like a post API
  * Dislike a post API
  * Get top 10/50 liked/disliked posts API [Optional filter of user id]

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


## Spring security
* JWT authentication is provided for all the APIs apart from following two.
  * Register User API
  * Login API

* Above two APIs will return JWT in response, which can be used in Bearer Authorization to access other APIs. Default validity is 5 minutes, which can be changed in application.properties.

### Build Backend (SpringBoot Java)
```bash
# Maven Build : Navigate to the root folder where pom.xml is present 
mvn clean install
```

### Start the API and WebUI server
```bash
# Start the server (8095)
# port and other configurations for API servere is in [./src/main/resources/application.properties](/src/main/resources/application.properties) file

# If you build with maven jar location will be 
java -jar ./target/app-1.0.0.jar
```

### Accessing Application
Cpmponent         | URL                                      | Credentials
---               | ---                                      | ---
MySQL Database       |  jdbc:mysql://localhost:3306/social_media        |  User Name:`root`,password:`root`   
Swagger (API Ref) |  http://localhost:9119/swagger-ui/index.html   | 
Swagger Spec      |  http://localhost:8095/v3/api-docs          |



### Screenshots
#### Login
![Dashboard](/screenshots/login.png?raw=true)
---
#### Dashboard - Order Stats
![Dashboard](/screenshots/order_stats.png?raw=true)
---
#### Dashboard - Product Stats
![Dashboard](/screenshots/product_stats.png?raw=true)
---
#### Orders
![Dashboard](/screenshots/orders.png?raw=true)
---
#### Orders Details
![Dashboard](/screenshots/order_details.png?raw=true)
---
#### Customers
![Dashboard](/screenshots/customers.png?raw=true)
---
#### API Docs - With Live Tryout
![Dashboard](/screenshots/api_doc.png?raw=true)
---
#### API Docs - For redability
![Dashboard](/screenshots/api_doc2.png?raw=true)
---
#### Database Schema
![ER Diagram](/screenshots/db_schema.png?raw=true)
