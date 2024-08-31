package com.flickfinder.controller;

import java.sql.SQLException;
import java.util.List;

import com.flickfinder.dao.PersonDAO;
import com.flickfinder.model.Movie;
import com.flickfinder.model.Person;

import io.javalin.http.Context;


public class PersonController {

	private final PersonDAO personDAO;
	
	public PersonController(PersonDAO personDAO) {
		this.personDAO=personDAO;
		
	}
	// to complete the must-have requirements you need to add the following methods:
	// getAllPeople
	// getPersonById
	// you will add further methods for the more advanced tasks; however, ensure your have completed 
	// the must have requirements before you start these.  
	
	public void getAllPeople(Context ctx) {
		try {
			if (ctx.queryParam("limit") != null) {
				if (!ctx.queryParam("limit").matches("\\d+")) {
					ctx.status(400);
					ctx.result("Limit is not a positive integer");
					return;
				}
				int limit= Integer.parseInt(ctx.queryParam("limit"));
				ctx.json(personDAO.getAllPeople(limit));
				return;
			}
			
			ctx.json(personDAO.getAllPeople());
			
		}catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
			e.printStackTrace();
			
		}
		
	}
	
	public void getPersonById(Context ctx) {
		try {
			int id = Integer.parseInt(ctx.pathParam("id"));
			
			Person person = personDAO.getPersonById(id);
			if (person == null) {
				ctx.status(404);
				ctx.result("Person not found for this id");
				return;
			}
			ctx.json(personDAO.getPersonById(id));
		} catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
			e.printStackTrace();
		}
	}
	public void getMoviesStarringPerson(Context ctx) {
		try {
			int id = Integer.parseInt(ctx.pathParam("id"));
			
			List<Movie> movies = personDAO.getMoviesStarringPerson(id);
			if (movies == null) {
				ctx.status(404);
				ctx.result("Stars not found");
				
			}
			ctx.json(movies);
		} catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
			e.printStackTrace();
		}
		
	}
}

