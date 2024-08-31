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

	// for the must have requirements, you will need to implement the following
	// methods:
	// - getAllPeople()
	// - getPersonById(int id)
	// you will add further methods for the more advanced tasks; however, ensure
	// your have completed
	// the must have requirements before you start these.
	public List<Person> getAllPeople(int limit) throws SQLException {
		List<Person> people = new ArrayList<>();

		String statement = "SELECT * FROM People LIMIT ?";
		PreparedStatement ps = connection.prepareStatement(statement);
		ps.setInt(1, limit);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			people.add(new Person(rs.getInt("id"), rs.getString("name"), rs.getInt("birth")));
		}

		if ((people.size() == 0) || (limit < 1)) {
			return null;
		} else {

			return people;
		}
	}

	public List<Person> getAllPeople() throws SQLException {
		List<Person> people = new ArrayList<>();

		String statement = "SELECT * FROM People LIMIT 50";
		PreparedStatement ps = connection.prepareStatement(statement);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			people.add(new Person(rs.getInt("id"), rs.getString("name"), rs.getInt("birth")));
		}

		if (people.size()==0){
			return null;
		}else {

		return people;
	}
	}

	public Person getPersonById(int id) throws SQLException {

		String statement = "SELECT * FROM People WHERE id = ?";
		PreparedStatement ps = connection.prepareStatement(statement);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {

			return new Person(rs.getInt("id"), rs.getString("name"), rs.getInt("birth"));
		}

		// return null if the id does not return a movie.

		return null;

	}

	public List<Movie> getMoviesStarringPerson(int id) throws SQLException {

		List<Movie> movies = new ArrayList<>();

		String statement = "SELECT id, title, year " + " FROM movies " + "JOIN stars  ON movies.id = stars.movie_id "
				+ "WHERE stars.person_id = ?";

		PreparedStatement ps = connection.prepareStatement(statement);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			movies.add(new Movie(rs.getInt("id"), rs.getString("title"), rs.getInt("year")));
		}
		if (movies.size()==0){
			return null;
		}else {

		return movies;
	}
	}

}
