package com.flickfinder.controller;

import java.sql.SQLException;

import com.flickfinder.dao.MovieDAO;
import com.flickfinder.dao.PersonDAO;
import com.flickfinder.model.Movie;
import com.flickfinder.model.Person;

import io.javalin.http.Context;

public class PersonController {
	// personDao Object
	private final PersonDAO personDao;

	public PersonController(PersonDAO personDao) {
		this.personDao = personDao;
	}

	// M.1 C.1 Retrieve a list of people and limit this list
	public void getAllPeople(Context ctx) {
		int limitParam;
		if (ctx.queryParam("limit") != null) {
			limitParam = Integer.parseInt(ctx.queryParam("limit"));
		} else {
			limitParam = 50;
		}
		try {
			ctx.json(personDao.getAllPeople(limitParam));
		} catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
		}
	}

	// M.4 Retrieve a person via their ID
	public void getPersonById(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		try {
			Person person = personDao.getPersonById(id);
			if (person == null) {
				ctx.status(404);
				ctx.result("Movie not found");
				return;
			}
			ctx.json(personDao.getPersonById(id));
		} catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
		}
	}

	// S.2 Retrieve a list of movies by a specific star
	public void getMoviesStarringPerson(Context ctx) {
		try {
			int id = Integer.parseInt(ctx.pathParam("id"));
			ctx.json(personDao.getMoviesByStar(id));
		} catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
		} 
	}
}