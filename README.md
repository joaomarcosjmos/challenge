# API Challenge
Project created to serve as a reference for testing analysis and technical knowledge.
## Dependencies
* Java 11
## How to run the application
* In memory
```
- git clone https://github.com/joaomarcosjmos/challenge.git
- cd challenge
- mvn spring-boot:run
```

* How test
```
- Importar arquivo collection no Postman
- The collection with test requests can be found in the file challende.postman_collection.json
```

### Changes and corrections made to the project:
* Added H2 dependency
* Changed invalid date of insert sql script
* Refactoring duplicate or meaningless method names in controller
* Decreased coupling of the flow of creation of DTOs classes
* - Creating three more exception scenarios
* - - IllegalArgumentException
* - - DataIntegrityViolationException
* - - EmptyResultDataAccessException
* Created search with pagination for getMovies
* Created search by date
* Changed DefaultMovieService class name to follow implemented services convention
* Increased MovieValidator test coverage on revenue attribute in generated exception
* Change status code from creation method to CREATED
* Created Actor entity and auxiliary classes
* Created actor search endpoint by movie id
* Created projection to search for actor by movie id
* Test coverage for the CRUD and validation flows.


