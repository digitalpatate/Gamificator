# **Gamificator**

> November 2020 -January 2021

![Gamificator](images/welcome.jpg)

Table of contents
=================

   * [Table of contents](#table-of-contents)
   * [Build and run](#Build-and-run-the-Application-microservice)
   * [Docker](#Docker)
* [Tests](#tests)
  * [Run test executable](#Test-the-Application-microservice-by-running-the-executable-specification)
* [Contributors](#Contributors)

## 

# Build and run the Application microservice

You can use maven to build and run the REST API implementation from the command line. After invoking the following maven goal, the Spring Boot server will be up and running, listening for connections on port 8080.

```bash
cd impl/
mvn spring-boot:run
```

You can then access:

* the [API documentation](http://localhost:8080/swagger-ui.html), generated from annotations in the code
* the [API endpoint](http://localhost:8080/), accepting GET and POST requests

You can use curl to invoke the endpoints:

* To retrieve the list of fruits previously created:

```bash
curl -X GET --header 'Accept: application/json' 'http://localhost:8080/applications'
```

* To create a new Application (beware that in the live documentation, there are extra \ line separators in the JSON payload that cause issues in some shells)

```bash
curl -X POST --header 'Content-Type: application/json' --header 'Accept: */*' -d '{
  "name": "My awesome app ",
  "url": "http://localhost:8080/my-awesome-app"
}' 'http://localhost:8080/applications'
```

# Docker

Information is stored in a database. It must be running before launching the spring-boot server. At the root of the project run the following command :

```bash
docker-compose up -d
```

This will launch a MySQL server dockerized. The credentials and the database connection are already setup in the spring-boot server.



# Tests

## Test the Application microservice by running the executable specification

You can use the Cucumber project to validate the API implementation. Do this when the server is running.

```bash
cd cd specs/
mvn clean test
```
You will see the test results in the console, but you can also open the file located in `./target/cucumber`

## Contributors

- Simon Walther - simon.walther@heig-vd.ch
- Didier Page - didier.page@heig-vd.ch
- Eric Noel - eric.noel@heig-vd.ch
- Guillaume Laubscher -  guillaume.laubscher@heig-vd.ch
- Bruno Legrand - bruno.legrand@heig-vd.ch