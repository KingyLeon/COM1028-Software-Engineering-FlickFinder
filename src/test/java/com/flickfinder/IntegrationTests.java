package com.flickfinder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.flickfinder.util.Database;
import com.flickfinder.util.Seeder;

import io.javalin.Javalin;

/**
 * These are our integration tests. We are testing the application as a whole,
 * including the database.
 */
class IntegrationTests {

	/**
	 * The Javalin app.*
	 */
	Javalin app;

	/**
	 * The seeder object.
	 */
	Seeder seeder;

	/**
	 * The port number. Try and use a different port number from your main
	 * application.
	 */
	int port = 6000;

	/**
	 * The base URL for our test application.
	 */
	String baseURL = "http://localhost:" + port;

	/**
	 * Bootstraps the application before each test.
	 */
	@BeforeEach
	void setUp() {
		var url = "jdbc:sqlite::memory:";
		seeder = new Seeder(url);
		Database.getInstance(seeder.getConnection());
		app = AppConfig.startServer(port);
	}

	/**
	 * Test that the application retrieves a list of all movies. Notice how we are
	 * checking the actual content of the list. At this higher level, we are not
	 * concerned with the implementation details.
	 */

	@Test
	void retrieves_a_list_of_all_movies() {
		given().when().get(baseURL + "/movies").then().assertThat().statusCode(200). // Assuming a successful
		// response returns HTTP
		// 200
				body("id", hasItems(1, 2, 3, 4, 5))
				.body("title",
						hasItems("The Shawshank Redemption", "The Godfather", "The Godfather: Part II",
								"The Dark Knight", "12 Angry Men"))
				.body("year", hasItems(1994, 1972, 1974, 2008, 1957));
	}

	/*
	 * Test that the application retrieves a specified movie by ID. Check that
	 * appropriate data is returned
	 */

	@Test
	void retrieves_a_single_movie_by_id() {
		given().when().get(baseURL + "/movies/1").then().assertThat().statusCode(200). // Assuming a successful
		// response returns HTTP
		// 200
				body("id", equalTo(1)).body("title", equalTo("The Shawshank Redemption")).body("year", equalTo(1994));
	}

	/*
	 * Test that the application retrieves a list of all people. Check appropriate
	 * data is returned
	 */
	@Test
	void retrieves_a_list_of_all_people() {
		given().when().get(baseURL + "/people").then().assertThat().statusCode(200). // Assuming a successful
		// response returns HTTP 200
				body("id", hasItems(1, 2, 3, 4, 5))
				.body("name",
						hasItems("Tim Robbins", "Morgan Freeman", "Christopher Nolan", "Al Pacino", "Henry Fonda"))
				.body("birth", hasItems(1958, 1937, 1970, 1940, 1905));
	}

	/*
	 * Test that the application retrieves a single person based on a provided id.
	 * Checks appropriate data is returned based on the person
	 */

	@Test
	void retrieves_a_single_person_by_id() {
		given().when().get(baseURL + "/people/1").then().assertThat().statusCode(200). // Assuming a successful
		// response returns HTTP200
				body("id", equalTo(1)).body("name", equalTo("Tim Robbins")).body("birth", equalTo(1958));
	}

	/*
	 * Test that the application retrieves a list of stars for a specific movie
	 * Checks appropriate data is returned based on the movie
	 */
	
	@Test
	void retrieves_stars_of_a_movie() {
		given().when().get(baseURL + "/movies/1/stars").then().assertThat().statusCode(200). // Assuming a successful
		// response returns HTTP 200
				body("id", hasItems(1, 2)).body("name", hasItems("Tim Robbins", "Morgan Freeman"))
				.body("birth", hasItems(1958, 1937));
	}

	/*
	 * Test that the application retrieves a list of movies based on a person and their id.
	 * Checks appropriate data is returned based on the person
	 */
	
	@Test
	void retrieves_movies_of_a_star() {
		given().when().get(baseURL + "/people/4/movies").then().assertThat().statusCode(200). // Assuming a successful
		// response returns HTTP 200
				body("id", hasItems(2, 3)).body("title", hasItems("The Godfather", "The Godfather: Part II"))
				.body("year", hasItems(1972, 1974));
	}

	/*
	 * Test that the application retrieves a list of movies and their attributes based on the year.
	 * Checks appropriate data is returned based on the person
	 */
	
	@Test
	void retrieves_Movies_by_Rating() {
		given().when().get(baseURL + "/movies/ratings/1974").then().assertThat().statusCode(200). // Assuming a
																									// successful
				body("id", hasItems(3)).body("title", hasItems("The Godfather: Part II")).body("year", hasItems(1974))
				.body("rating", hasItems(9.0f));
	}

	/**
	 * Tears down the application after each test. We want to make sure that each
	 * test runs in isolation.
	 */

	@AfterEach
	void tearDown() {
		seeder.closeConnection();
		app.stop();
	}

}
