# Banking Assignment

### Description

This assignment aimed to show, how we can develop microservices from scratch using spring boot.

The application has 2 microservices and 1 eureka server spring boot application.

1. account-service:
   - Provides APIs for Account related operations like opening Bank Account, fetching bank account details.
   - Application runs on 8762 port.
   - Database name: accountDB
   - Database credentials: sa/password
   - H2 database console: http://localhost:8762/h2-console/
2. transaction-service: 
   - Provides APIs for transactions like credit & debit, It also stores account_id for which the transaction has been done.
   - Application runs on 8763 port.
   - Database name: transactionDB
   - Database credentials: sa/password
   - H2 database console: http://localhost:8763/h2-console/
3. eureka-server: 
   - Provides registry to register different microservices.
   - url: http://localhost:8761/

### Components used to develop this application :

1. Java 11
2. Spring Boot
3. Spring Cloud
4. H2 Database
5. Maven
6. Mapstruct
7. Swagger for Contract First Approach

### How to run the app

Following are the steps to run the application

1. Run maven build to build all the components (refer following command to build all the components)
```
mvn clean install
```

2. Start eureka-server application
```
cd <PROJECT_HOME>/eureka-server
mvn spring-boot:run
```

3. Start transaction-service application
```
cd <PROJECT_HOME>/transaction-service
mvn spring-boot:run
```

4. Start account-service application
```
cd <PROJECT_HOME>/account-service
mvn spring-boot:run
```

### Scope of Improvement

1. Authentication & Authorization needs to be added with API Gateway.
2. CI/CD pipeline needs to be setup with the combination of Docker.
3. Circuit Breaker & SAGA pattern needs to be implemented.
4. Centralized swagger access of swagger file.
