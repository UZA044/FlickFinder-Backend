package com.flickfinder.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.flickfinder.dao.MovieDAO;

import io.javalin.http.Context;

/**
 * Test for the Movie Controller.
 */

class MovieControllerTest {

	/**
	 *
	 * The context object, later we will mock it.
	 */
	private Context ctx;

	/**
	 * The movie data access object.
	 */
	private MovieDAO movieDAO;

	/**
	 * The movie controller.
	 */

	private MovieController movieController;

	@BeforeEach
	void setUp() {
		// We create a mock of the MovieDAO class.
		movieDAO = mock(MovieDAO.class);
		// We create a mock of the Context class.
		ctx = mock(Context.class);

		// We create an instance of the MovieController class and pass the mock object
		movieController = new MovieController(movieDAO);
	}

	/**
	 * Tests the getAllMovies method.
	 * We expect to get a list of all movies in the database.
	 */

	@Test
	void testGetAllMovies() {
		movieController.getAllMovies(ctx);
		try {
			verify(movieDAO).getAllMovies();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testGetAllMoviesWithLimit() {
		when(ctx.queryParam("limit")).thenReturn("3");
		movieController.getAllMovies(ctx);
		try {
			verify(movieDAO).getAllMovies(3);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testGetAllMoviesWithNegativeIntegerLimit() {
		when(ctx.queryParam("limit")).thenReturn("-3");
		movieController.getAllMovies(ctx);
		verify(ctx).status(400);
	}
	
	@Test
	void testGetAllMoviesWithCharcaterAsLimit() {
		when(ctx.queryParam("limit")).thenReturn("a");
		movieController.getAllMovies(ctx);
		verify(ctx).status(400);
	}


	/**
	 * Test that the controller returns a 500 status code when a database error
	 * occurs
	 * 
	 * @throws SQLException
	 */
	@Test
	void testThrows500ExceptionWhenGetAllDatabaseError() throws SQLException {
		when(movieDAO.getAllMovies()).thenThrow(new SQLException());
		movieController.getAllMovies(ctx);
		verify(ctx).status(500);
	}

	/**
	 * Tests the getMovieById method.
	 * We expect to get the movie with the specified id.
	 */

	@Test
	void testGetMovieById() {
		when(ctx.pathParam("id")).thenReturn("1");
		movieController.getMovieById(ctx);
		try {
			verify(movieDAO).getMovieById(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test a 500 status code is returned when a database error occurs.
	 * 
	 * @throws SQLException
	 */

	@Test
	void testThrows500ExceptionWhenGetByIdDatabaseError() throws SQLException {
		when(ctx.pathParam("id")).thenReturn("1");
		when(movieDAO.getMovieById(1)).thenThrow(new SQLException());
		movieController.getMovieById(ctx);
		verify(ctx).status(500);
	}

	/**
	 * Test that the controller returns a 404 status code when a movie is not found
	 * or
	 * database error.
	 * 
	 * @throws SQLException
	 */

	@Test
	void testThrows404ExceptionWhenNoMovieFound() throws SQLException {
		when(ctx.pathParam("id")).thenReturn("1");
		when(movieDAO.getMovieById(1)).thenReturn(null);
		movieController.getMovieById(ctx);
		verify(ctx).status(404);
	}
	
	@Test
	void testGetPeopleByMovieId() {
		when(ctx.pathParam("id")).thenReturn("2");
		movieController.getPeopleByMovieId(ctx);
		try {
			verify(movieDAO).getPeopleByMovieId(2);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testThrows500ExceptionWhenGetPeopleByMovieIdDatabaseError() throws SQLException {
		when(ctx.pathParam("id")).thenReturn("1");
		when(movieDAO.getPeopleByMovieId(1)).thenThrow(new SQLException());
		movieController.getPeopleByMovieId(ctx);
		verify(ctx).status(500);
	}
	
	@Test
	void testThrows404ExceptionWhenNoPeopleByMovieIdFound() throws SQLException {
		when(ctx.pathParam("id")).thenReturn("1");
		when(movieDAO.getPeopleByMovieId(1)).thenReturn(null);
		movieController.getPeopleByMovieId(ctx);
		verify(ctx).status(404);
	}
	
	@Test
	void testgetRatingsByValidYear() {
		when(ctx.pathParam("year")).thenReturn("2008");
		movieController.getRatingsByYear(ctx);
		try {
			verify(movieDAO).getRatingsByYear(2008);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testThrows404ExceptionWhengetRatingsByInvalidYear() {
		when(ctx.pathParam("year")).thenReturn("1952");
		movieController.getRatingsByYear(ctx);
		verify(ctx).status(404);

	}
	
	
	@Test
	void testThrows500ExceptionWhengetRatingsByYearDatabaseError() throws SQLException {
		when(ctx.pathParam("year")).thenReturn("2020");
		when(movieDAO.getRatingsByYear(2020)).thenThrow(new SQLException());
		movieController.getRatingsByYear(ctx);
		verify(ctx).status(500);
	}
	
	@Test
	void testThrows500ExceptionWhengetRatingsByYearDatabaseErrorWithLimit() throws SQLException {
		when(ctx.pathParam("year")).thenReturn("2020");
		when(ctx.queryParam("limit")).thenReturn("20");

		when(movieDAO.getRatingsByYear(20,2020)).thenThrow(new SQLException());
		movieController.getRatingsByYear(ctx);
		verify(ctx).status(500);
	}
	
	@Test
	void testThrows500ExceptionWhengetRatingsByYearWithVotesDatabaseErrorWithVotes() throws SQLException {
		when(ctx.pathParam("year")).thenReturn("2020");
		when(ctx.queryParam("votes")).thenReturn("100000");

		when(movieDAO.getRatingsByYearWithVotes(2020,100000)).thenThrow(new SQLException());
		movieController.getRatingsByYear(ctx);
		verify(ctx).status(500);
	}
	
	@Test
	void testgetRatingsByValidYearWithLimit() {
		when(ctx.pathParam("year")).thenReturn("1972");
		when(ctx.queryParam("limit")).thenReturn("35");
		movieController.getRatingsByYear(ctx);
		try {
			verify(movieDAO).getRatingsByYear(35,1972);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testgetRatingsByValidYearWithInvalidNegativeLimit() {
		when(ctx.pathParam("year")).thenReturn("1972");
		when(ctx.queryParam("limit")).thenReturn("-35");
		movieController.getRatingsByYear(ctx);
		verify(ctx).status(400);
	}
	
	@Test
	void testgetRatingsByValidYearWithCharacterAsLimit() {
		when(ctx.pathParam("year")).thenReturn("1972");
		when(ctx.queryParam("limit")).thenReturn("a");
		movieController.getRatingsByYear(ctx);
		verify(ctx).status(400);
	}
	
	@Test
	void testgetRatingsByValidYearWithVotes() {
		when(ctx.pathParam("year")).thenReturn("1972");
		when(ctx.queryParam("votes")).thenReturn("10000");
		movieController.getRatingsByYear(ctx);
		try {
			verify(movieDAO).getRatingsByYearWithVotes(1972,10000);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testgetRatingsByValidYearWithCharacterAsVotes() {
		when(ctx.pathParam("year")).thenReturn("1972");
		when(ctx.queryParam("votes")).thenReturn("a");
		movieController.getRatingsByYear(ctx);
		verify(ctx).status(400);
	}
	
	@Test
	void testgetRatingsByValidYearWithInvalidNegativeVotes() {
		when(ctx.pathParam("year")).thenReturn("1972");
		when(ctx.queryParam("limit")).thenReturn("-63");
		movieController.getRatingsByYear(ctx);
		verify(ctx).status(400);
	}
	
	
			
		
	

}