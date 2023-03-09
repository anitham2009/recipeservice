How to run application in local
mvn clean install
mvn spring-boot:run

Run jar using below command 
java -jar <jar-location>\recipeservice-1.0.jar


Test endpoint
curl -X GET "http://localhost:8080/recipeservice/recipes"