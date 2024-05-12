package com.flickfinder.controller;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.flickfinder.dao.PersonDAO;

import io.javalin.http.Context;

/**
 * TODO: Implement this class
 */
class PersonControllerTest {
	/**
	 *
	 * The context object, later we will mock it.
	 */
	private Context ctx;

	/**
	 * The person data access object.
	 */
	private PersonDAO personDAO;

	/**
	 * The person controller.
	 */

	private PersonController personController;

	@BeforeEach
	void setUp() {
		// We create a mock of the personDAO class.
		personDAO = mock(PersonDAO.class);
		// We create a mock of the Context class.
		ctx = mock(Context.class);

		// We create an instance of the personController class and pass the mock object
		personController = new PersonController(personDAO);
	}

	/**
	 * Tests the getAllpersons method.
	 * We expect to get a list of all persons in the database.
	 */

	@Test
	void testGetAllpersons() {
		personController.getAllPeople(ctx);
		try {
			verify(personDAO).getAllPeople(50);
		} catch (SQLException e) {
			fail("SQLException occured");
		}
	}

	/**
	 * Tests the getpersonById method.
	 * We expect to get the person with the specified id.
	 */

	@Test
	void testGetpersonById() {
		when(ctx.pathParam("id")).thenReturn("1");
		personController.getPersonById(ctx);
		try {
			verify(personDAO).getPersonById(1);
		} catch (SQLException e) {
			fail("SQLException occured");
		}
	}
	
	@Test void testGetMoviesStarringPerson() {
		when(ctx.pathParam("id")).thenReturn("12");
		personController.getPersonById(ctx);
		try {
			verify(personDAO).getPersonById(12);
		} catch (SQLException e) {
			fail("SQLException occured");
		}
	}
	
	/*
	 * Test that the controller returns a 500 status code when a database error
	 * occurs
	 * 
	 * @throws SQLException
	 */
	@Test
	void testThrows500ExceptionWhenGetAllDatabaseError() throws SQLException {
		when(personDAO.getAllPeople(50)).thenThrow(new SQLException());
		personController.getAllPeople(ctx);
		verify(ctx).status(500);

	}
	
	/*
	 * Test that the controller returns a 500 status code when a database error
	 * occurs for getPersonById()
	 * 
	 * @throws SQLException
	 */
	@Test
	void testThrows500ExceptionWhenGetByIdDatabaseError() throws SQLException {
		when(ctx.pathParam("id")).thenReturn("1");
		when(personDAO.getPersonById(1)).thenThrow(new SQLException());
		personController.getPersonById(ctx);
		verify(ctx).status(500);
	}
	/*
	 * Test that the controller returns a 500 status code when a database error
	 * occurs
	 * 
	 * @throws SQLException
	 */
	@Test
	void testThrows500ExceptionWhenGetMoviesStartingPersonDatabaseError() throws SQLException {
		when(ctx.pathParam("id")).thenReturn("1");
		when(personDAO.getMoviesByStar(1)).thenThrow(new SQLException());
		personController.getMoviesStarringPerson(ctx);
		verify(ctx).status(500);
	}
}