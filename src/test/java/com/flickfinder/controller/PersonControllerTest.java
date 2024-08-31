package com.flickfinder.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.flickfinder.dao.MovieDAO;
import com.flickfinder.dao.PersonDAO;

import io.javalin.http.Context;
/**
 * TODO: Implement this class
 */
class PersonControllerTest {
	
	private Context ctx;

	private PersonDAO personDAO;
	
	private PersonController personController;
	
	@BeforeEach
	void setUp() {
		// We create a mock of the MovieDAO class.
		personDAO = mock(PersonDAO.class);
		// We create a mock of the Context class.
		ctx = mock(Context.class);

		// We create an instance of the MovieController class and pass the mock object
		personController = new PersonController(personDAO);
	}
	
	@Test
	void testGetAllMovies() {
		personController.getAllPeople(ctx);
		try {
			verify(personDAO).getAllPeople();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testGetAllMoviesWithLimit() {
		when(ctx.queryParam("limit")).thenReturn("3");
		personController.getAllPeople(ctx);
		try {
			verify(personDAO).getAllPeople(3);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testGetAllPeopleWithNegativeLimit() {
		when(ctx.queryParam("limit")).thenReturn("-2");
		personController.getAllPeople(ctx);
		verify(ctx).status(400);
	}
	
	@Test
	void testGetAllPeopleWithaCharacterAsLimit() {
		when(ctx.queryParam("limit")).thenReturn("a");
		personController.getAllPeople(ctx);
		verify(ctx).status(400);
	}
	
	
	
	@Test
	void testThrows500ExceptionWhenGetAllDatabaseError() throws SQLException {
		when(personDAO.getAllPeople()).thenThrow(new SQLException());
		personController.getAllPeople(ctx);
		verify(ctx).status(500);
	}
	
	@Test
	void testGetPersonById() {
		when(ctx.pathParam("id")).thenReturn("1");
		personController.getPersonById(ctx);
		try {
			verify(personDAO).getPersonById(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	void testThrows500ExceptionWhenGetByIdDatabaseError() throws SQLException {
		when(ctx.pathParam("id")).thenReturn("1");
		when(personDAO.getPersonById(1)).thenThrow(new SQLException());
		personController.getPersonById(ctx);
		verify(ctx).status(500);
	}
	
	@Test
	void testThrows404ExceptionWhenNoPersonFound() throws SQLException {
		when(ctx.pathParam("id")).thenReturn("1");
		when(personDAO.getPersonById(1)).thenReturn(null);
		personController.getPersonById(ctx);
		verify(ctx).status(404);
	}
	
	@Test
	void testGetMoviesStarringPerson() {
		when(ctx.pathParam("id")).thenReturn("4");
		personController.getMoviesStarringPerson(ctx);
		try {
			verify(personDAO).getMoviesStarringPerson(4);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testThrows500ExceptiongetMoviesStarringPersonDatabaseError() throws SQLException {
		when(ctx.pathParam("id")).thenReturn("1");
		when(personDAO.getMoviesStarringPerson(1)).thenThrow(new SQLException());
		personController.getMoviesStarringPerson(ctx);
		verify(ctx).status(500);
	}
	
	@Test
	void testThrows404ExceptionWhenNoMoviesFoundStarringPerson() throws SQLException {
		when(ctx.pathParam("id")).thenReturn("1");
		when(personDAO.getMoviesStarringPerson(1)).thenReturn(null);
		personController.getMoviesStarringPerson(ctx);
		verify(ctx).status(404);
	}


}