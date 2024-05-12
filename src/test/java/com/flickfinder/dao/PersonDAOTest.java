package com.flickfinder.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.flickfinder.model.Movie;
import com.flickfinder.model.Person;
import com.flickfinder.util.Database;
import com.flickfinder.util.Seeder;

/**
 * TODO: Implement this class
 */
class PersonDAOTest {
	/**
	 * The Person data access object.
	 */

	private PersonDAO personDAO;

	/**
	 * Seeder
	 */

	Seeder seeder;

	/**
	 * Sets up the database connection and creates the tables.
	 * We are using an in-memory database for testing purposes.
	 * This gets passed to the Database class to get a connection to the database.
	 * As it's a singleton class, the entire application will use the same
	 * connection.
	 */
	@BeforeEach
	void setUp() {
		var url = "jdbc:sqlite::memory:";
		seeder = new Seeder(url);
		Database.getInstance(seeder.getConnection());
		personDAO = new PersonDAO();

	}

	/**
	 * Tests the getAllPersons method.
	 * We expect to get a list of all Persons in the database.
	 * We have seeded the database with 5 Persons, so we expect to get 5 Persons back.
	 * At this point, we avoid checking the actual content of the list.
	 */
	@Test
	void testGetAllPeople() {
		try {
			List<Person> person = personDAO.getAllPeople(5);
			assertEquals(5, person.size());
		} catch (SQLException e) {
			fail("SQLException thrown");
			e.printStackTrace();
		}
	}
	
	@Test
	void testGetAllPeopleLimit() {
		try {
			List<Person> people = personDAO.getAllPeople(2);
			assertEquals(2, people.size());
		} catch(SQLException e) {
			fail("SQLException thrown");
			e.printStackTrace();
		}
	}

	/**
	 * Tests the getPersonById method.
	 * We expect to get the Person with the specified id.
	 */
	@Test
	void testGetPersonById() {
		Person person;
		try {
			person = personDAO.getPersonById(1);
			assertEquals("Tim Robbins", person.getName());
		} catch (SQLException e) {
			fail("SQLException thrown");
			e.printStackTrace();
		}
	}

	/**
	 * Tests the getPersonById method with an invalid id. Null should be returned.
	 */
	@Test
	void testGetPersonByInvalidId() {
		try {
			Person person = personDAO.getPersonById(100000);
			assertEquals(null, person.getName());
		} catch (SQLException e) {
			fail("SQLException thrown");
			e.printStackTrace();
		}

	}
	
	/**
	 * Test getting all getMoviesByStar, should return all movies featuring a certain star
	 */
	@Test
	void testGetMoviesByStar() {
		List<Movie> movies;
		try {
			movies = personDAO.getMoviesByStar(4);
			assertEquals("[Movie [id=2, title=The Godfather, year=1972], Movie [id=3, title=The Godfather: Part II, year=1974]]", movies.toString());
		} catch (SQLException e) {
			fail("SQLException thrown");
			e.printStackTrace();
		}
	}

	@AfterEach
	void tearDown() {
		seeder.closeConnection();
	}
}