package com.flickfinder.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.flickfinder.model.Movie;
import com.flickfinder.model.MovieRating;
import com.flickfinder.model.Person;
import com.flickfinder.util.Database;
import com.flickfinder.util.Seeder;

/**
 * Test for the Movie Data Access Object. This uses an in-memory database for
 * testing purposes.
 */

class MovieDAOTest {

	/**
	 * The movie data access object.
	 */

	private MovieDAO movieDAO;

	/**
	 * Seeder
	 */

	Seeder seeder;

	/**
	 * Sets up the database connection and creates the tables. We are using an
	 * in-memory database for testing purposes. This gets passed to the Database
	 * class to get a connection to the database. As it's a singleton class, the
	 * entire application will use the same connection.
	 */
	@BeforeEach
	void setUp() {
		var url = "jdbc:sqlite::memory:";
		seeder = new Seeder(url);
		Database.getInstance(seeder.getConnection());
		movieDAO = new MovieDAO();

	}

	/**
	 * Tests the getAllMovies method. We expect to get a list of all movies in the
	 * database. We have seeded the database with 5 movies, so we expect to get 5
	 * movies back. At this point, we avoid checking the actual content of the list.
	 */

	@Test
	void testGetAllMovies() {
		try {
			List<Movie> movies = movieDAO.getAllMovies(5);
			assertEquals(5, movies.size());
		} catch (SQLException e) {
			fail("SQLException thrown");
			e.printStackTrace();
		}
	}

	void testGetAllMoviesLimit() {
		try {
			List<Movie> movies = movieDAO.getAllMovies(3);
			assertEquals(3, movies.size());
		} catch (SQLException e) {
			fail("SQLException thrown");
		}
	}

	/**
	 * Tests the getMovieById method. We expect to get the movie with the specified
	 * id.
	 */
	@Test
	void testGetMovieById() {
		Movie movie;
		try {
			movie = movieDAO.getMovieById(1);
			assertEquals("The Shawshank Redemption", movie.getTitle());
		} catch (SQLException e) {
			fail("SQLException thrown");
			e.printStackTrace();
		}
	}

	/*
	 * Tests the getStarsByMovie method. We expect to get a list of stars returned
	 * related to a specified movie id
	 */

	@Test
	void testGetStarsByMovie() {
		List<Person> people;
		try {
			people = movieDAO.getStarsByMovie(1);
			assertEquals("Tim Robbins", people.get(0).getName());
			assertEquals("Morgan Freeman", people.get(1).getName());
		} catch (SQLException e) {
			fail("SQLException thrown");
			e.printStackTrace();
		}
	}

	/*
	 * Tests the getMovieRatingsByYear method. We expect a list of movies from the
	 * same year.
	 */

	@Test
	void testGetMovieRatingsByYear() {
		List<MovieRating> movies;
		movies = movieDAO.getMovieRatingsByYear(1974, 10, 100);
		assertEquals("The Godfather: Part II", movies.get(0).getTitle());
		assertEquals(9.0, movies.get(0).getRating());
	}

	@AfterEach
	void tearDown() {
		seeder.closeConnection();
	}

}