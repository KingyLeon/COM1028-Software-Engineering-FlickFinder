## COM1028 Software Engineering - FlickFinder

This is the starting project for the COM1028 Software Engineering module. This project is a simple application that provides a RESTful API for accessing a movie database. The database is a simple SQLite database that contains information about movies, people, and their relationships.

## Dependencies

Overall, we have the following dependencies in our project:

- [sqllite-jdbc](https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc) - SQLite JDBC Driver
- [javalin](https://javalin.io/) - A simple web framework for Java
- [junit](https://junit.org/junit5/) - JUnit for unit testing
- [mockito](https://site.mockito.org/) - Mockito for mocking objects in unit tests
- [rest-assured](https://rest-assured.io/) - Testing and validating our APIs

These dependencies are managed by Maven, and you can find them in the [pom.xml](pom.xml) file.

## Database

### Development Database

The database is a simple SQLite database that contains information about movies, people, and their relationships. The database is structured as follows:

![Database](https://csee.pages.surrey.ac.uk/com1028/res/ERD.png)

You've been given the IMDB movies database to work with. It is a simple database and you will only need to interact with it in a read only manner; you will not be adding data or modifying the database.

You can find the database in the [src/main/resources](src/main/resources) folder. The database is called `movies.db`. You should not modify this database in any way. However, it won't be there until you run the project for the first time. I am using the com.googlecode.maven-download-plugin to pull the database in from a remote location. This is defined in the [pom.xml](pom.xml) file. It should be pulled when you run the project for the first time.

### Testing Database

Although the development database is simple, it has a lot of data. This can make testing difficult. For testing we use a in-memory database. This database is created and populated with data before each test and destroyed after each test. This code can be found in [src/test/java/com/flickfinder/util/Seeder.java](src/test/java/com/flickfinder/util/Seeder.java).
