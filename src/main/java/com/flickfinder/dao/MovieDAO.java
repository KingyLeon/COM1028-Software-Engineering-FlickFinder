 package com.flickfinder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.flickfinder.model.Movie;
import com.flickfinder.model.MovieRating;
import com.flickfinder.model.Person;
import com.flickfinder.util.Database;

/**
 * The Data Access Object for the Movie table.
 * 
 * This class is responsible for getting data from the Movies table in the
 * database.
 * 
 */
public class MovieDAO {

	/**
	 * The connection to the database.
	 */
	private final Connection connection;

	/**
	 * Constructs a SQLiteMovieDAO object and gets the database connection.
	 * 
	 */
	public MovieDAO() {
		Database database = Database.getInstance();
		connection = database.getConnection();
	}

	/**
	 * @return a list of all movies in the database
	 * @throws SQLException if a database error occurs
	 */

	// M.1 List All Movies from a database
	// C.1 Get Limited Number of Movies
	public List<Movie> getAllMovies() throws SQLException {
		List<Movie> movies = new ArrayList<>();
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM movies LIMIT 50");

		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			movies.add(new Movie(rs.getInt("id"), rs.getString("title"), rs.getInt("year")));
		}

		return movies;
	}

	public List<Movie> getAllMovies(int limit) throws SQLException {
		List<Movie> movies = new ArrayList<>();
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM movies LIMIT ?");
		statement.setInt(1, limit);

		if (limit > 0) {
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				movies.add(new Movie(rs.getInt("id"), rs.getString("title"), rs.getInt("year")));
			}
		}
		return movies;
	}

	/**
	 * @param id the id of the movie
	 * @return the movie with the specified id
	 * @throws SQLException if a database error occurs
	 */

	// M.2 Get Movie by ID
	public Movie getMovieById(int id) throws SQLException {

		String statement = "select * from movies where id = ?";
		PreparedStatement ps = connection.prepareStatement(statement);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			return new Movie(rs.getInt("id"), rs.getString("title"), rs.getInt("year"));
		}
		// return null if the id does not return a movie.
		return null;
	}

	// S.1: Retrieve a list of stars by a specific movie.
	public List<Person> getStarsByMovie(int id) throws SQLException {
		List<Integer> personID = new ArrayList<Integer>();

		String statement = "select * from stars where movie_id = ?";
		PreparedStatement ps = connection.prepareStatement(statement);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			personID.add(rs.getInt("person_id"));
		}

		List<Person> people = new ArrayList<Person>();
		for (Integer x : personID) {
			PersonDAO person = new PersonDAO();
			people.add(person.getPersonById(x));
		}
		// return null if the id does not any stars.
		return people;
	}

	/*
	 * Returns Movies by rating for a certain year
	 * 
	 * @param year of movies released
	 * 
	 * @return List of movies specified by rating
	 */
	public List<MovieRating> getMovieRatingsByYear(int year, int limit, int votes) {
		List<MovieRating> movies = new ArrayList<>();
		PreparedStatement statement;

		try {
			statement = connection.prepareStatement(
					"SELECT * FROM movies JOIN ratings ON movies.id = ratings.movie_id WHERE year = ? AND votes > ? ORDER BY RATINGS.rating DESC LIMIT ?");
			statement.setInt(1, year);
			statement.setInt(2, votes);
			statement.setInt(3, limit);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				movies.add(new MovieRating(rs.getInt("id"), rs.getString("title"), rs.getInt("year"),
						rs.getFloat("rating"), rs.getInt("votes")));
			}
			return movies;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return movies;
	}
}
