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

This is an RESTfull implementation of an order processing app based on Northwind database schema from Microsoft.
The goal of the project is to 
- Highlight techniques of making and securing a REST full app using [SpringBoot](https://projects.spring.io/spring-boot)
- How to consume an RESTfull service and make an HTML5 based Single Page App using [Angular 4+](https://github.com/angular/angular)

### Features of the Project
* Backend
  * Token Based Security (using Spring security)
  * API documentation and Live Try-out links with Swagger 
  * In Memory DB with H2 
  * Using JPA and JDBC template to talk to relational database
  * How to request and respond for paginated data 

* Frontend
  * Organizing Components, Services, Directives, Pages etc in an Angular App
  * How to chain RxJS Observables (by making sequntial AJAX request- its different that how you do with promises)
  * Techniques to Lazy load Data (Infinite Scroll)
  * Techniques to load large data set in a data-table but still keeping DOM footprint less
  * Routing and guarding pages that needs authentication
  * Basic visulaization

* Build
  * How to build all in one app that includes (database, sample data, RESTfull API, Auto generated API Docs, frontend and security)
  * Portable app, Ideal for dockers, cloud hosting.

## In Memory DB (H2)
I have included an in-memory database for the application. Database schema and sample data for the app is created everytime the app starts, and gets destroyed after the app stops, so the changes made to to the database are persistent only as long as the app is running
<br/>
Creation of database schema and data are done using sql scripts that Springs runs automatically. 
To modify the database schema or the data you can modify [schema.sql](./src/main/resources/schema.sql) and [data.sql](./src/main/resources/data.sql) which can be found at `/src/main/resources`

## Spring security
Security is **enabled** by default, to disable, you must comment [this line](./src/main/java/com/app/config/SecurityConfig.java#L15) in `src/main/java/com/config/SecurityConfig.java`<br/>
When security is enabled, none of the REST API will be accessesble directly.

To test security access `http://localhost:9119/version` API and you should get a forbidden/Access denied error. 


### Build Backend (SpringBoot Java)
```bash
# Maven Build : Navigate to the root folder where pom.xml is present 
mvn clean install

### Start the API and WebUI server
```bash
# Start the server (8095)
# port and other configurations for API servere is in [./src/main/resources/application.properties](/src/main/resources/application.properties) file

# If you build with maven jar location will be 
java -jar ./target/app-1.0.0.jar


### Accessing Application
Cpmponent         | URL                                      | Credentials
---               | ---                                      | ---
MySQL Database       |  jdbc:mysql://localhost:3306/social_media        |  User Name:`root`,password:`root`   
Swagger (API Ref) |  http://localhost:9119/swagger-ui/index.html   | 
Swagger Spec      |  http://localhost:8095/v3/api-docs          |



**To get an authentication token** 



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


## Backers

Thank you to all our backers! 🙏 [[Become a backer](https://opencollective.com/angular-springboot-rest-jwt#backer)]

<a href="https://opencollective.com/angular-springboot-rest-jwt#backers" target="_blank"><img src="https://opencollective.com/angular-springboot-rest-jwt/backers.svg?width=890"></a>


## Sponsors

Support this project by becoming a sponsor. Your logo will show up here with a link to your website. [[Become a sponsor](https://opencollective.com/angular-springboot-rest-jwt#sponsor)]
