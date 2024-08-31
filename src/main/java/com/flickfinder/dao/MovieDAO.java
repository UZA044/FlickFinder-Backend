package com.flickfinder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	 * Returns a list of all movies in the database.
	 * 
	 * @return a list of all movies in the database
	 * @throws SQLException if a database error occurs
	 */

	public List<Movie> getAllMovies(int limit) throws SQLException {
		List<Movie> movies = new ArrayList<>();
		
		
		String statement = "select * from movies LIMIT ?";
		PreparedStatement ps = connection.prepareStatement(statement);
		ps.setInt(1, limit);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			movies.add(new Movie(rs.getInt("id"), rs.getString("title"), rs.getInt("year")));
		}
		if ((movies.size()==0)||(limit<1)){
			return null;
			
		}else {
			return movies;
			
		}
		
	}
	
	public List<Movie> getAllMovies() throws SQLException {
		List<Movie> movies = new ArrayList<>();

		
		
		String statement = "select * from movies LIMIT 50";
		PreparedStatement ps = connection.prepareStatement(statement);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			movies.add(new Movie(rs.getInt("id"), rs.getString("title"), rs.getInt("year")));
		}
		
		if (movies.size()==0) {
			return null;
		}else {
		return movies;
		}
	}

	/**
	 * Returns the movie with the specified id.
	 * 
	 * @param id the id of the movie
	 * @return the movie with the specified id
	 * @throws SQLException if a database error occurs
	 */
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
	
	public List<Person> getPeopleByMovieId(int id) throws SQLException {
		List <Person> stars = new ArrayList<>();
		
		String statement = "SELECT id, name, birth " +" FROM people "
				+ "JOIN stars  ON people.id = stars.person_id " + "WHERE stars.movie_id = ?";
		
		PreparedStatement ps = connection.prepareStatement(statement);
		ps.setInt(1, id);
		ResultSet rs =ps.executeQuery();
		while (rs.next()) {
			stars.add(new Person(rs.getInt("id"),rs.getString("name"),rs.getInt("birth")));
		}
		if (stars.size()==0) {
			return null;
		}else {
			
		return stars;}
	}
	
	public List<MovieRating> getRatingsByYear(int limit, int year) throws SQLException {
		List<MovieRating> movies = new ArrayList<>();

		
		
		String statement = "SELECT id, title,rating, votes, year  " +" FROM movies "
				+ "JOIN ratings ON movies.id = ratings.movie_id " + "WHERE movies.year = ? AND ratings.votes > ?"
						+ " ORDER BY ratings.rating DESC LIMIT ?";
		PreparedStatement ps = connection.prepareStatement(statement);
		ps.setInt(1, year);
		ps.setInt(2, 1000);
		ps.setInt(3, limit);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			movies.add(new MovieRating(rs.getInt("id"), rs.getString("title"),rs.getDouble("rating"), rs.getInt("votes"), rs.getInt("year")));
		}

		if ((movies.size()==0)||(limit<1)) {
			return null;
		}else {
			return movies;

		}
		
	}
	public List<MovieRating> getRatingsByYearWithVotes(int year, int votes) throws SQLException {
		List<MovieRating> movies = new ArrayList<>();
		

		
		
		String statement = "SELECT id, title,rating, votes, year  " +" FROM movies "
				+ "JOIN ratings ON movies.id = ratings.movie_id " + "WHERE movies.year = ? AND ratings.votes > ?"
						+ "ORDER BY ratings.rating DESC LIMIT ?";
		PreparedStatement ps = connection.prepareStatement(statement);
		ps.setInt(1, year);
		ps.setInt(2, votes);
		ps.setInt(3, 50);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			movies.add(new MovieRating(rs.getInt("id"), rs.getString("title"),rs.getDouble("rating"), rs.getInt("votes"), rs.getInt("year")));
		}

		if (movies.size()==0) {
			return null;
		}else {
			return movies;

		}
		
	}
	public List<MovieRating> getRatingsByYear(int year) throws SQLException {
		List<MovieRating> movies = new ArrayList<>();

		
		
		String statement = "SELECT id, title,rating, votes, year " +" FROM movies "
				+ "JOIN ratings ON movies.id = ratings.movie_id " + "WHERE movies.year = ? AND ratings.votes > ?"
						+ " ORDER BY ratings.rating DESC LIMIT ?";
		PreparedStatement ps = connection.prepareStatement(statement);
		ps.setInt(1, year);
		ps.setInt(2, 1000);
		ps.setInt(3, 50);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			movies.add(new MovieRating(rs.getInt("id"), rs.getString("title"), rs.getDouble("rating"), rs.getInt("votes"), rs.getInt("year")));
		}
		
		if (movies.size()==0) {
			return null;
		}else {
			return movies;

		}
		
	}

}
