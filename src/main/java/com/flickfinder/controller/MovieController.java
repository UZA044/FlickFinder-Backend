package com.flickfinder.controller;

import java.sql.SQLException;
import java.util.List;

import com.flickfinder.dao.MovieDAO;
import com.flickfinder.model.Movie;
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
		try {
			if (ctx.queryParam("limit") != null) {
				if (!ctx.queryParam("limit").matches("\\d+")) {
					ctx.status(400);
					ctx.result("Limit is not a positive integer");
					return;
				}
				int limit = Integer.parseInt(ctx.queryParam("limit"));
				ctx.json(movieDAO.getAllMovies(limit));
				return;
			}

			ctx.json(movieDAO.getAllMovies());

		} catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
			e.printStackTrace();

		}

	}

	/**
	 * Returns the movie with the specified id.
	 * 
	 * @param ctx the Javalin context
	 */
	public void getMovieById(Context ctx) {

		try {
			int id = Integer.parseInt(ctx.pathParam("id"));
			Movie movie = movieDAO.getMovieById(id);
			if (movie == null) {
				ctx.status(404);
				ctx.result("Movie not found for this id");
				return;
			}
			ctx.json(movieDAO.getMovieById(id));
		} catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
			e.printStackTrace();
		}
	}

	public void getPeopleByMovieId(Context ctx) {
		try {
			int id = Integer.parseInt(ctx.pathParam("id"));
			List<Person> stars = movieDAO.getPeopleByMovieId(id);
			if (stars == null) {
				ctx.status(404);
				ctx.result("Stars not found for this id");

			}
			ctx.json(stars);
		} catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
			e.printStackTrace();
		}

	}

	public void getRatingsByYear(Context ctx) {
		int year = Integer.parseInt(ctx.pathParam("year"));

		try {
			if ((year <= 1969) || (year >= 2030)) {
				ctx.status(404);
				ctx.result("Year is not between 1969 and 2030");
				return;
			}

			if (ctx.queryParam("limit") != null) {
				if (!ctx.queryParam("limit").matches("\\d+")) {
					throw new IllegalArgumentException("Limit is not a Positive Integer");
				}
				int limit = Integer.parseInt(ctx.queryParam("limit"));
				ctx.json(movieDAO.getRatingsByYear(limit, year));
				return;

			} else if (ctx.queryParam("votes") != null) {

				if (!ctx.queryParam("votes").matches("\\d+")) {
					throw new IllegalArgumentException("Votes is not a Positive Integer");
				}
				int votes = Integer.parseInt(ctx.queryParam("votes"));
				ctx.json(movieDAO.getRatingsByYearWithVotes(year, votes));
				return;

			}

			else {

				ctx.json(movieDAO.getRatingsByYear(year));

			}
		} catch (SQLException e) {
			ctx.status(500);
			ctx.result("Database error");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			ctx.status(400);
			ctx.result(e.getMessage());
			e.printStackTrace();
		}

	}

}