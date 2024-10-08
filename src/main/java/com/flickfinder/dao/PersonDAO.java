package com.flickfinder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.flickfinder.model.Movie;
import com.flickfinder.model.Person;
import com.flickfinder.util.Database;

/**
 * TODO: Implement this class
 * 
 */
public class PersonDAO {

	private final Connection connection;

	public PersonDAO() {
		Database database = Database.getInstance();
		connection = database.getConnection();
	}
	
	// Retrieves a list of all (50) people, or a limited number of people

	public List<Person> getAllPeople(int limit) throws SQLException {
		List<Person> people = new ArrayList<>();

		PreparedStatement statement = connection.prepareStatement("SELECT * FROM people LIMIT ?");
		statement.setInt(1, limit);

		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			people.add(new Person(rs.getInt("id"), rs.getString("Name"), rs.getInt("birth")));
		}

		return people;
	}

	public Person getPersonById(int id) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("select * from people where id = ?");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		return new Person(rs.getInt("id"), rs.getString("name"), rs.getInt("birth"));
	}

	// S.2: Retrieve a list of movies by a specific star.
	public List<Movie> getMoviesByStar(int id) throws SQLException {
		List<Integer> starsID = new ArrayList<Integer>();

		String statement = "select * from stars where person_id = ?";
		PreparedStatement ps = connection.prepareStatement(statement);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			starsID.add(rs.getInt("movie_id"));
		}

		List<Movie> movies = new ArrayList<Movie>();
		for (Integer x : starsID) {
			MovieDAO movie = new MovieDAO();
			movies.add(movie.getMovieById(x));
		}
		return movies;
	}

}
