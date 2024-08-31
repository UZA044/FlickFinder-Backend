package com.flickfinder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.flickfinder.util.Database;
import com.flickfinder.util.Seeder;

import io.javalin.Javalin;

/**
 * These are our integration tests.
 * We are testing the application as a whole, including the database.
 */
class IntegrationTests {

	/**
	 * The Javalin app.*
	 */
	Javalin app;

	/**
	 * The seeder object.
	 */
	Seeder seeder;

	/**
	 * The port number. Try and use a different port number from your main
	 * application.
	 */
	int port = 6000;

	/**
	 * The base URL for our test application.
	 */
	String baseURL = "http://localhost:" + port;

	/**
	 * Bootstraps the application before each test.
	 */
	@BeforeEach
	void setUp() {
		var url = "jdbc:sqlite::memory:";
		seeder = new Seeder(url);
		Database.getInstance(seeder.getConnection());
		app = AppConfig.startServer(port);
	}

	/**
	 * Test that the application retrieves a list of all movies.
	 * Notice how we are checking the actual content of the list.
	 * At this higher level, we are not concerned with the implementation details.
	 */

	@Test
	void retrieves_a_list_of_all_movies() {
		given().when().get(baseURL + "/movies").then().assertThat().statusCode(200). // Assuming a successful
												// response returns HTTP
												// 200
				body("id", hasItems(1, 2, 3, 4, 5))
				.body("title", hasItems("The Shawshank Redemption", "The Godfather",
						"The Godfather: Part II", "The Dark Knight", "12 Angry Men"))
				.body("year", hasItems(1994, 1972, 1974, 2008, 1957));
	}
	
	@Test
	void retrieves_a_list_of_all_movies_with_limit() {
		given().when().get(baseURL + "/movies?limit=3").then().assertThat().statusCode(200). // Assuming a successful
												// response returns HTTP
												// 200
				body("id", hasItems(1, 2, 3))
				.body("title", hasItems("The Shawshank Redemption", "The Godfather",
						"The Godfather: Part II"))
				.body("year", hasItems(1994, 1972, 1974));
	}
	
	@Test
	void retrieves_a_list_of_all_movies_with_InvalidNegativelimit() {
		given().when().get(baseURL + "/movies?limit=-3").then().assertThat().statusCode(400). 
												
				body(equalTo("Limit is not a positive integer"));
				
	}
	
	@Test
	void retrieves_a_list_of_all_movies_with_Invalidlimit() {
		given().when().get(baseURL + "/movies?limit=a").then().assertThat().statusCode(400). 
												
				body(equalTo("Limit is not a positive integer"));
				
	}
	
	
	@Test
	void retrieves_a_list_of_all_people() {
		given().when().get(baseURL + "/people").then().assertThat().statusCode(200). // Assuming a successful
												// response returns HTTP
												// 200
				body("id", hasItems(1, 2, 3, 4, 5))
				.body("name", hasItems("Tim Robbins", "Morgan Freeman",
						"Christopher Nolan", "Al Pacino", "Henry Fonda"))
				.body("birth", hasItems(1958, 1937, 1970, 1940, 1905));
	}
	
	@Test
	void retrieves_a_list_of_all_people_with_limit() {
		given().when().get(baseURL + "/people?limit=2").then().assertThat().statusCode(200). // Assuming a successful
												// response returns HTTP
												// 200
				body("id", hasItems(1, 2))
				.body("name", hasItems("Tim Robbins", "Morgan Freeman"))
				.body("birth", hasItems(1958, 1937));
	}
	
	@Test
	void retrieves_a_list_of_all_people_with_InvalidNegativelimit() {
		given().when().get(baseURL + "/people?limit=-2").then().assertThat().statusCode(400). 
		
				
				body(equalTo("Limit is not a positive integer"));

				
	}
	
	@Test
	void retrieves_a_list_of_all_people_with_Invalidlimit() {
		given().when().get(baseURL + "/people?limit=a").then().assertThat().statusCode(400). 
		
				
				body(equalTo("Limit is not a positive integer"));

				
	}

	
	@Test
	void retrieves_a_single_movie_by_id() {

		given().when().get(baseURL + "/movies/1").then().assertThat().statusCode(200). // Assuming a successful
												// response returns HTTP
												// 200
				body("id", equalTo(1))
				.body("title", equalTo("The Shawshank Redemption"))
				.body("year", equalTo(1994));
	}
	
	@Test
	void retrieves_a_single_movie_by_Invalid_id() {

		given().when().get(baseURL + "/movies/10000").then().assertThat().statusCode(404).
												
		
				body(equalTo("Movie not found for this id"));
	}
	
	
	
	@Test
	void retrieves_a_single_person_by_id() {

		given().when().get(baseURL + "/people/1").then().assertThat().statusCode(200). // Assuming a successful
												// response returns HTTP
												// 200
				body("id", equalTo(1))
				.body("name", equalTo("Tim Robbins"))
				.body("birth", equalTo(1958));
	}
	
	@Test
	void retrieves_a_single_person_by_Invalid_id() {

		given().when().get(baseURL + "/people/10000").then().assertThat().statusCode(404).
												
		
				body(equalTo("Person not found for this id"));
	}
	
	@Test
	void retrieves_a_list_of_all_people_starring_by_movie_id() {
		given().when().get(baseURL + "/movies/1/stars").then().assertThat().statusCode(200). // Assuming a successful
												// response returns HTTP
												// 200
				body("id", hasItems(1,2))
				.body("name", hasItems("Tim Robbins", "Morgan Freeman"))
				.body("birth", hasItems(1958, 1937));
	}
	@Test
	void retrieves_a_list_of_all_movies_starred_by_person_id_() {
		given().when().get(baseURL + "/people/4/movies").then().assertThat().statusCode(200). // Assuming a successful
												// response returns HTTP
												// 200
				body("id", hasItems(2,3))
				.body("title", hasItems("The Godfather", "The Godfather: Part II"))
				.body("year", hasItems(1972, 1974));
	}
	@Test
	void retrieves_a_list_of_ratings_by_year_with_limit() {
		given().when().get(baseURL + "/movies/ratings/2008?limit=2").then().assertThat().statusCode(200). // Assuming a successful
												// response returns HTTP
												// 200
				body("id", hasItems(4, 6))
				.body("title", hasItems("The Dark Knight", "Fast And Furious 7"))
				.body("year", hasItems(2008, 2008));
	}
	
	@Test
	void retrieves_a_list_of_ratings_by_year_with_Invalid_limit() {
		given().when().get(baseURL + "/movies/ratings/2008?limit=-2").then().assertThat().statusCode(400).
												
		
				body(equalTo("Limit is not a Positive Integer"));
	}
	
	@Test
	void retrieves_a_list_of_ratings_by_year_with_Invalid_String_limit() {
		given().when().get(baseURL + "/movies/ratings/2008?limit=b").then().assertThat().statusCode(400).
												
		
				body(equalTo("Limit is not a Positive Integer"));
	}
	@Test
	void retrieves_a_list_of_ratings_by_year_with_no_limit() {
		given().when().get(baseURL + "/movies/ratings/2008").then().assertThat().statusCode(200). // Assuming a successful
												// response returns HTTP
												// 200
				body("id", hasItems(4,6,7,8))
				.body("title", hasItems("The Dark Knight", "Fast And Furious 7","Dark Planet","The Day The Earth Stood Still"))
				.body("year", hasItems(2008, 2008,2008,2008));
	}
	@Test
	void retrieves_a_list_of_ratings_by_Invalid_year_with_no_limit() {
		given().when().get(baseURL + "/movies/ratings/1901").then().assertThat().statusCode(404). 
		
		
				body(equalTo("Year is not between 1969 and 2030"));
	}
	@Test
	void retrieves_a_list_of_ratings_by_year_with_Invalid_votes() {
		given().when().get(baseURL + "/movies/ratings/2008?votes=-170000").then().assertThat().statusCode(400). 
												
		
				body(equalTo("Votes is not a Positive Integer"));
	}
	@Test
	void retrieves_a_list_of_ratings_by_year_with_Invalid_String_votes() {
		given().when().get(baseURL + "/movies/ratings/2008?votes=a").then().assertThat().statusCode(400). 
												
		
				body(equalTo("Votes is not a Positive Integer"));
	}
	/**
	 * Tears down the application after each test.
	 * We want to make sure that each test runs in isolation.
	 */
	@AfterEach
	void tearDown() {
		seeder.closeConnection();
		app.stop();
	}

}
