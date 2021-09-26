# How to run the code
### 1. Clone the repository
    git clone https://github.com/jemandrade/backend-test-57blocks.git

### 2. Open the project and update the following fields in the application.properties file with your local database information
    spring.datasource.username=<database name>
    spring.datasource.password=<database password>
    spring.datasource.url=<database url>
    
### 3. Run the application as a Spring Boot Application
![image](https://user-images.githubusercontent.com/91394479/134789375-aa8f82c8-9a2f-46b4-b8c3-cef879c0a03d.png)

By doing this, the `data.sql` and `schema.sql` scripts should be executed and the tables and initial data needed for the project will be created.


# How to test the code
### You can quickly test the code by using swagger ui:
#### 1. With the project running, navigate to [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) where you can see the API Documentation and test the endpoints.
![image](https://user-images.githubusercontent.com/91394479/134792335-b73834cd-0450-4b36-84d6-52c3e6a952f2.png)

#### 2. The first thing is creating a user using the registration endpoint `/Users (createUser)`
![image](https://user-images.githubusercontent.com/91394479/134792648-0b8bdee4-57aa-4513-9ecb-7b6a210503c2.png)

#### 3. Next you have to login using the `/Users (createJwtToken)` endpoint in order to get the authorization token
![image](https://user-images.githubusercontent.com/91394479/134792699-c0424e4f-9ec6-40df-8709-3e2a35380615.png)

#### 4. Then you can use the authorization token during 20 minutes to test the `/movies` endpoints. Remember to add 'Bearer ' at the beginning of the authorization field.
![image](https://user-images.githubusercontent.com/91394479/134792885-14eaa231-8558-4d0b-8d1c-ac6a0c3393b1.png)

