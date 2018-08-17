# README #

### Requirements ###
Update `application.yml` appropriately.
Remember to add data first and provide correct data source urls in application.yml
Important Note: It expects schema and tables to be created already in databases


### Building the artifact ###

```
mvn clean package
```

### Running the application from command line ###

```
mvn spring-boot:run
```

### Available URLs


```
curl -v -H "X-TENANT-ID: tenant_1" "http://localhost:8800/v1/user/{username}"
curl -v -H "X-TENANT-ID: tenant_2" "http://localhost:8800/v1/user/{username}"
curl -v -H "X-TENANT-ID: tenant_1" -H "Content-Type: application/json" "http://localhost:8800/v1/user" -X POST --data '{"username":"jaiswal741","firstname":"Akshay","lastname":"Jaiswal","contactNo":"7218736656"}'
```

TODO: Need to configure flyway to create schema and tables in all databases