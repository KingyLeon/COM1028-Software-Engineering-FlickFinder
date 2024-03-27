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

	// to complete the must-have requirements you need to add the following methods:
	// getAllPeople
	public void getAllPeople(Context ctx) {
		try {
			ctx.json(personDao.getAllPeople());
		} catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
			e.printStackTrace();
		}
	}

	public void getNumPeople(Context ctx) {
		if (ctx.queryParam("limit") != null) {
			int limitParam = Integer.parseInt(ctx.queryParam("limit"));
			if (limitParam > 0) {
				PersonDAO personDAO = new PersonDAO();
				try {
					ctx.json(personDAO.getNumPeople(limitParam));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			getAllPeople(ctx);
		}
	}

	// getPersonById
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
			e.printStackTrace();
		}
	}

	public void getMoviesStarringPerson(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		PersonDAO PersonDAO = new PersonDAO();
		try {
			ctx.json(PersonDAO.getMoviesByStar(id));
		} catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
			e.printStackTrace();
		}
	}
	// you will add further methods for the more advanced tasks; however, ensure
	// your have completed
	// the must have requirements before you start these.

}