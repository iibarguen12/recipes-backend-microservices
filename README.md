# recipes-backend-microservices
## Recipes backend app with microservices approach 

This project implements the Gateway Pattern distributing every functionality in a microservices and isolated the clients from each service using Spring Cloud Feign, Eureka Service Discovery to enabling client-side load-balancing and Spring Security with JWT for the authentication and authorization.

The microservices included are:
- eureka-server
- auth-service
- user-service
- recipe-service

For the persistence there are two different MySQL databases. One for the user-service with the information of the users and the roles, which also is used for the auth-service to look up user validation, and other for the recipe-service with the information of the recipes, food categories and ingredients. with this approach a sharding database strategy can be implemented in the future.

Every microservice has it's own unit tests, integration tests, and Dockerfile configuration to be containerize and then be deployed as cluster with a docker-compose. With this containers and the help of Eureka we can deploy many instance of one service, and register them to be used with the default round-robin load balancer in order to scale the application.

The microservices are exposed on different internal ports, but none of them are exposed outside the container cluster, except for the 8080 which is used by the auth-service to redirect the traffic using Feign to the others microservices.

The user-service inserts a first user when it's start using the commandline runner begin able to login with the follow credentials in localhost:8080 :
- user: abn_user
- pass: user

- user: abn_admin
- pass: admin

The different endpoints to acces are:

1. Users:
  - Login: http://localhost:8080/login Method: Get
  - Get User: http://localhost:8080/v1/users/{id} Method: Get
  - Get Users: http://localhost:8080/v1/users Method: Get
  - Add User: http://localhost:8080/v1/users Method: Post
  - Delete User: http://localhost:8080/v1/users/{id} Method: Delete
  - Get Roles: http://localhost:8080/v1/roles Method: Get
  - Add Role to User: http://localhost:8080/v1/roles/user Method: Post
2. Recipes:
  - Get Recipe: http://localhost:8080/v1/recipes/{id} Method: Get
  - Get Recipes: http://localhost:8080/v1/recipes Method: Get
    * possible parameters to pass and filter:
        * title
        * username
        * foodCategoryDto 
  - Add Recipes: http://localhost:8080/v1/recipes Method: Post
  - Delete Recipe: http://localhost:8080/v1/recipes/{id} Method: Delete
3. Ingredients:
  - Get Ingredient: http://localhost:8080/v1/ingredients/{id} Method: Get
  - Get Ingredients: http://localhost:8080/v1/ingredients Method: Get
  - Add Ingredient: http://localhost:8080/v1/ingredients Method: Post
  - Delete Ingredient: http://localhost:8080/v1/ingredients/{id} Method: Delete
4. Food Categories:
  - Get Food Category: http://localhost:8080/v1/food-categories/{id} Method: Get
  - Get Food Categories: http://localhost:8080/v1/food-categories Method: Get
  - Add Food Category: http://localhost:8080/v1/food-categories Method: Post
  - Delete Food Category: http://localhost:8080/v1/food-categories/{id} Method: Delete
