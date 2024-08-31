package com.flickfinder.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class MovieRatingTest {
	
	private MovieRating movieRating;
	
	@BeforeEach
	public void setUp() {
		movieRating = new MovieRating(1, "The Matrix",9.5,1500,1999);
	}
	
	/**
	 * Test the movieRating object is created with the correct values.
	 */
	@Test
	public void testMovieRatingCreated() {
		assertEquals(1, movieRating.getId());
		assertEquals("The Matrix", movieRating.getTitle());
		assertEquals(1999, movieRating.getYear());
		assertEquals(9.5, movieRating.getRating());
		assertEquals(1500,movieRating.getVotes());
	}

	/**
	 * Test the movieRating object is created with the correct values.
	 */
	@Test
	public void testMovieRatingSetters() {
		movieRating.setId(2);
		movieRating.setTitle("The Matrix Reloaded");
		movieRating.setYear(2003);
		movieRating.setRating(7.3);
		movieRating.setVotes(283);
		assertEquals(2, movieRating.getId());
		assertEquals("The Matrix Reloaded", movieRating.getTitle());
		assertEquals(2003, movieRating.getYear());
		assertEquals(7.3, movieRating.getRating());
		assertEquals(283,movieRating.getVotes());
	}

}
