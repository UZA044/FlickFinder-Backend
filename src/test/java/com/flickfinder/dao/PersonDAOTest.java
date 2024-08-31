package com.flickfinder.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.flickfinder.model.Movie;
import com.flickfinder.model.Person;
import com.flickfinder.util.Database;
import com.flickfinder.util.Seeder;

/**
 * TODO: Implement this class
 */
class PersonDAOTest {
	
	private PersonDAO personDAO;
	
	Seeder seeder;
	
	
	@BeforeEach
	void setUp() {
		var url = "jdbc:sqlite::memory:";
		seeder = new Seeder(url);
		Database.getInstance(seeder.getConnection());
		personDAO = new PersonDAO();

	}
	
	@Test
	void testGetAllPeople() {
		try {
			List<Person> people = personDAO.getAllPeople();
			assertEquals(5,people.size());
		} catch (SQLException e) {
			fail("SQLException thrown");
			e.printStackTrace();
		}
	}
	
	@Test
	void testGetAllPeopleWithLimit() {
		int limit_test=2;
		try {
			List<Person> people = personDAO.getAllPeople(limit_test);
			assertEquals(limit_test,people.size());
		} catch (SQLException e) {
			fail("SQLException thrown");
			e.printStackTrace();
		}
	}
	
	@Test
	void testGetAllPeopleWithInvalidLimit() {
		int limit_test=-2;
		try {
			List<Person> people = personDAO.getAllPeople(limit_test);
			assertEquals(null,people);
		} catch (SQLException e) {
			fail("SQLException thrown");
			e.printStackTrace();
		}
	}
	
	@Test
	void testGetPersonById() {
		try {
			Person person = personDAO.getPersonById(1);
			assertEquals("Tim Robbins", person.getName());
		} catch (SQLException e) {
			fail("SQLException thrown");
			e.printStackTrace();
		}

	
	}
	
	@Test
	void testGetPersonByIdInvalidId() {
		try {
			Person person = personDAO.getPersonById(1000);
		} catch (SQLException e) {
			assertEquals("SQLException thrown",e.getMessage());
			e.printStackTrace();
		}

	}
	@Test
	void testGetMoviesStarringPerson() {
		try {
			List<Movie> movies = personDAO.getMoviesStarringPerson(4);
			assertEquals(2,movies.size());
		} catch (SQLException e) {
			fail("SQLException thrown");
			e.printStackTrace();
		}
	}
	
	@Test
	void testGetMoviesStarringPersonInvalidId() {
		try {
			List<Movie> movies = personDAO.getMoviesStarringPerson(1000);
		} catch (SQLException e) {
			assertEquals("SQLException thrown",e.getMessage());
			e.printStackTrace();
		}
	}

	@AfterEach
	void tearDown() {
		seeder.closeConnection();
	}
	

}