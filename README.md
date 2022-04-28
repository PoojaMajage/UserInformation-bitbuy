User Account Information -Bitbuy

This is a sample Java / Maven / Spring Boot (version 1.5.6) application that can be used as a starter for creating a microservice complete with built-in health check, metrics and much more. I hope it helps you.

**How to Run**

Clone this repository
Make sure you are using JDK 1.8 and Maven 3.x
You can build the project running "mvn clean package"
Once successfully built, you can run the service by one of these two methods:

java -jar target/userinformation-0.0.1-SNAPSHOT.jar
or
mvn spring-boot:run

Once the application runs you should see something like this
2022-04-28 14:50:42.254  INFO 48206 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2022-04-28 14:50:42.261  INFO 48206 --- [           main] c.b.u.UserInformationApplication         : Started UserInformationApplication in 2.431 seconds (JVM running for 2.648)

**About the Service**
The service is just a User Information REST service. It uses an in-memory database (H2) to store the data.
You can create an account, login using the username and password and then access/modify the user information.
Here are some endpoints you can call:

POST http://localhost:8091/api/register : to register the account
{
"username": "alex",
"password": "alex123"
}

Response: 
{
"id": "d36d2ae1-ef68-406c-8967-19a863a93753",
"username": "pooja",
"password": "pooja",
"token": null
}

POST http://localhost:8091/api/login    : to login to the account
{
"username": "alex",
"password": "alex123"
}

Response:
{
"id": "ded6e5cf-cd4e-45b3-913d-4e1545ed8415",
"username": "pooja",
"password": "pooja",
"token": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoicG9vamEiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjUxMTcyNDU1LCJleHAiOjE2NTExNzMwNTV9.OYMYBEn72IUlOh12LziGmZ_w_hPZ6bELXJ6TWJAMr4v800X1G2B0t_AfSzw5LmVtMztYYq0xsJBrQwmMTS2tuw"
}

GET http://localhost:8091/api/users/{id}: to get the user information
1. Use the token to add in header with Key as Authorization and values as the response token from login call
2. Add the id rom the response in login as UUID in the url

Response:
{
"name": null,
"email": null,
"phone": null
}

PUT http://localhost:8091/api/users/{id}: to modify the user information
1. Use the token to add in header with Key as Authorization and values as the response token from login call
2. Add the id rom the response in login as UUID in the url
{
 "name": "alex",
 "email": "alex@hotmail.com",
 "phone": "4567894561"
}
Response:
{
 "name": "alex",
 "email": "alex@hotmail.com",
 "phone": "4567894561"
}

To view your H2 in-memory database
To view and query the database you can browse to http://localhost:8080/h2-console. 
Default username is 'sa' with a blank password.