# TrainingREST

# Build and run the Fruit microservice

You can use maven to build and run the REST API implementation from the command line. After invoking the following maven goal, the Spring Boot server will be up and running, listening for connections on port 8080.

```
cd swagger/spring-server/
mvn spring-boot:run
```

You can then access:

* the [API documentation](http://localhost:8080/api/swagger-ui.html), generated from annotations in the code
* the [API endpoint](http://localhost:8080/api/), accepting GET and POST requests

You can use curl to invoke the endpoints:

* To retrieve the list of fruits previously created:

```
curl -X GET --header 'Accept: application/json' 'http://localhost:8080/api/fruits'
```

* To create a new fruit (beware that in the live documentation, there are extra \ line separators in the JSON payload that cause issues in some shells)

```
curl -X POST --header 'Content-Type: application/json' --header 'Accept: */*' -d '{
   "colour": "orange",
   "kind": "orange", 
   "size": "small", 
   "weight": "medium" 
 }' 'http://localhost:8080/api/fruits'
 ```

# Test the Fruit microservice by running the executable specification

You can use the Cucumber project to validate the API implementation. Do this when the server is running.

```
cd cd swagger/fruits-specs/
mvn clean test
```
You will see the test results in the console, but you can also open the file located in `./target/cucumber/index.html`

