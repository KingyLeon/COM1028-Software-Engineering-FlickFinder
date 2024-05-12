package com.flickfinder.controller;

import java.sql.SQLException;
import java.util.List;

import com.flickfinder.dao.MovieDAO;
import com.flickfinder.dao.PersonDAO;
import com.flickfinder.model.Movie;
import com.flickfinder.model.MovieRating;
import com.flickfinder.model.Person;

import io.javalin.http.Context;

/**
 * The controller for the movie endpoints.
 * 
 * The controller acts as an intermediary between the HTTP routes and the DAO.
 * 
 * As you can see each method in the controller class is responsible for
 * handling a specific HTTP request.
 * 
 * Methods a Javalin Context object as a parameter and uses it to send a
 * response back to the client. We also handle business logic in the controller,
 * such as validating input and handling errors.
 *
 * Notice that the methods don't return anything. Instead, they use the Javalin
 * Context object to send a response back to the client.
 */

public class MovieController {

	/**
	 * The movie data access object.
	 */

	private final MovieDAO movieDAO;

	/**
	 * Constructs a MovieController object and initializes the movieDAO.
	 */

	public MovieController(MovieDAO movieDAO) {
		this.movieDAO = movieDAO;
	}

	/**
	 * Returns a list of all movies in the database.
	 * 
	 * @param ctx the Javalin context
	 */

	public void getAllMovies(Context ctx) {
		int limitParam;
		if (ctx.queryParam("limit") != null) {
			limitParam = Integer.parseInt(ctx.queryParam("limit"));
		} else {
			limitParam = 50;
		}
		try {
			ctx.json(this.movieDAO.getAllMovies(limitParam));
		} catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
		}
	}

	/**
	 * Returns the movie with the specified id.
	 * 
	 * @param ctx the Javalin context
	 */
	public void getMovieById(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		try {
			Movie movie = movieDAO.getMovieById(id);
			if (movie == null) {
				ctx.status(404);
				ctx.result("Movie not found");
				return;
			}
			ctx.json(movieDAO.getMovieById(id));
		} catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
		}
	}

	/*
	 * Returns people by specified movie id
	 */
	public void getPeopleByMovieId(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		try {
			ctx.json(movieDAO.getStarsByMovie(id));
		} catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
			e.printStackTrace();
		}
	}

	public void getRatingsByYear(Context ctx) throws SQLException {
		int yearParam = 0;
		int limitParam = 0;
		int voteParam = 0;
		try {
			yearParam = Integer.parseInt(ctx.pathParam("year"));
			limitParam = Integer.parseInt(ctx.queryParam("limit"));
		} catch (NumberFormatException e) {
			limitParam = 50;
		}
		try {
			voteParam = Integer.parseInt(ctx.queryParam("votes"));
		} catch (NumberFormatException e) {
			voteParam = 1000;
		}

		ctx.json(this.movieDAO.getMovieRatingsByYear(yearParam, limitParam, voteParam));
	}
}