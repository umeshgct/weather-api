Weather API
---

Welcome to the Spring Boot Rest API  based Weather application.

### Prerequisite:

 1) Java 8   
 2) Springboot 2.1.6   
 3) apache Maven    
 4) H2 Database (file will be created in home path)

### Steps to run

 1) Build the Weather application project using mvn clean install
 2) Run the application using mvn spring-boot:run
 3) The web application is accessible via localhost:8080
 4) Use username : Test and password : Test123 to login to weather application.
 ### Steps to test the application

 1) Application URL: Swagger URL: http://localhost:8080/swagger-ui.html.
 2) In the swagger page please enter the city name that you want to fetch the weather information.
 3) Enter the API key value as  cc22fdb65b00778a6de49a488ebe7438
 4) Click the execute the button to fetch the weather information. This would fetch the latest weather information 
    from open weather API also persist the same in DB.    
 5) This sample url would help you to access the application from browser.
  "http://localhost:8080/weather-service/v1/weather?cityName=London&apiKey=cc22fdb65b00778a6de49a488ebe7438"  
 6) Access to H2 console http://localhost:8080/h2-console.
    user : sa     password : password
 

### Footnote

I have used in memory database as it would fasten the development also to avoid additional db installation.

Weather information saved every time in DB whenever its requested from API since weather is not a constant value.

API key also constantly changing thats why kept as input parameter.

