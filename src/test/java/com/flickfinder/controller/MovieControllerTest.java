package com.flickfinder.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.flickfinder.dao.MovieDAO;

import io.javalin.http.Context;

/**
 * Test for the Movie Controller.
 */

class MovieControllerTest {

	/**
	 * The context object, later we will mock it.
	 */
	private Context ctx;

	/**
	 * The movie data access object.
	 */
	private MovieDAO movieDAO;

	/**
	 * The movie controller.
	 */

	private MovieController movieController;

	@BeforeEach
	void setUp() {
		// We create a mock of the MovieDAO class.
		movieDAO = mock(MovieDAO.class);
		// We create a mock of the Context class.
		ctx = mock(Context.class);

		// We create an instance of the MovieController class and pass the mock object
		movieController = new MovieController(movieDAO);
	}

	/**
	 * Tests the getAllMovies method. We expect to get a list of all movies in the
	 * database.
	 */

	@Test
	void testGetAllMovies() {
		movieController.getAllMovies(ctx);
		try {
			verify(movieDAO).getAllMovies(50);
		} catch (SQLException e) {
			e.printStackTrace();
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
		when(movieDAO.getAllMovies(50)).thenThrow(new SQLException());
		movieController.getAllMovies(ctx);
		verify(ctx).status(500);

	}

	/**
	 * Tests the getMovieById method.
	 * We expect to get the movie with the specified id.
	 */

	@Test
	void testGetMovieById() {
		when(ctx.pathParam("id")).thenReturn("1");
		movieController.getMovieById(ctx);
		try {
			verify(movieDAO).getMovieById(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests the getPeopleByMovieId method.
	 * We expect to get a list of all people related to the MovieId in the database.
	 */
	
	@Test
	void testGetPeopleByMovieId() {
		when(ctx.pathParam("id")).thenReturn("1");
		movieController.getPeopleByMovieId(ctx);
		try {
			verify(movieDAO).getStarsByMovie(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/*
	 * Tests the getRatingsByYear method. We expect an ordered list returned of
	 * movies from a certain year above a certain number of votes
	 */
	@Test
	void testGetRatingsByYear() {
		when(ctx.pathParam("year")).thenReturn("2000");
		when(ctx.queryParam("limit")).thenReturn("2");
		when(ctx.queryParam("votes")).thenReturn("100");
		try {
			movieController.getRatingsByYear(ctx);
			verify(movieDAO).getMovieRatingsByYear(2000, 2, 100);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Tests default vote and limit value
	// We expect an ordered list returned of 50 movies from a certain year above a
	// 1000 votes

	@Test
	void testNoVoteNoLimitParamGetRatingsByYear() {
		when(ctx.pathParam("year")).thenReturn("2000");
		try {
			movieController.getRatingsByYear(ctx);
			verify(movieDAO).getMovieRatingsByYear(2000, 50, 1000);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test a 500 status code is returned when a database error occurs.
	 * @throws SQLException
	 */

	@Test
	void testThrows500ExceptionWhenGetByIdDatabaseError() throws SQLException {
		when(ctx.pathParam("id")).thenReturn("1");
		when(movieDAO.getMovieById(1)).thenThrow(new SQLException());
		movieController.getMovieById(ctx);
		verify(ctx).status(500);
	}
	
	/**
	 * Tests that the controller returns status 500 if no valid star found
	 * @throws SQLException
	 */
	
	@Test
	void testThrows500WhenGetPeopleByMovieIdError() throws SQLException {
		when(movieDAO.getStarsByMovie(1)).thenThrow(new SQLException());
		movieController.getPeopleByMovieId(ctx);
		verify(ctx).status(500);
	}


	/**
	 * Test that the controller returns a 404 status code when a movie is not found
	 * or
	 * database error.
	 * @throws SQLException
	 */

	@Test
	void testThrows404ExceptionWhenNoMovieFound() throws SQLException {
		when(ctx.pathParam("id")).thenReturn("1");
		when(movieDAO.getMovieById(1)).thenReturn(null);
		movieController.getMovieById(ctx);
		verify(ctx).status(404);
	}

}