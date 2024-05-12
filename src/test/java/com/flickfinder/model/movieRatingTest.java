package com.flickfinder.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class movieRatingTest {


	/**
	 * The movieRating object to be tested.
	 */
	private MovieRating movieR;

	/**
	 * Set up the movieRating object before each test.
	 *
	 */
	@BeforeEach
	public void setUp() {
		movieR = new MovieRating(1, "The Matrix", 1999, 9.0, 400000);
	}

	/**
	 * Test the movieRating object is created with the correct values.
	 */
	@Test
	public void testmovieRatingCreated() {
		assertEquals(1, movieR.getId());
		assertEquals("The Matrix", movieR.getTitle());
		assertEquals(1999, movieR.getYear());
	}

	/**
	 * Test the movieRating object is created with the correct values.
	 */
	@Test
	public void testmovieRatingSetters() {
		movieR.setId(2);
		movieR.setTitle("The Matrix Reloaded");
		movieR.setYear(2003);
		movieR.setRating(9.0);
		movieR.setVotes(400000);
		assertEquals(2, movieR.getId());
		assertEquals("The Matrix Reloaded", movieR.getTitle());
		assertEquals(2003, movieR.getYear());
		assertEquals(9.0, movieR.getRating());
		assertEquals(400000, movieR.getVotes());
	}
}