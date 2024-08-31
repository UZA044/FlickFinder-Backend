package com.flickfinder.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 * TODO: Implement this class
 * 
 */

class PersonTest {
	
	private Person person;
	
	@BeforeEach
	public void setUp() {
		person = new Person(1, "Joe Smith", 1987);
	}
	
	@Test
	public void testPersonCreated() {
		assertEquals(1, person.getId());
		assertEquals("Joe Smith", person.getName());
		assertEquals(1987, person.getBirth());
	}

	/**
	 * Test the movie object is created with the correct values.
	 */
	@Test
	public void testPersonSetters() {
		person.setId(2);
		person.setName("Tom Cruise");
		person.setBirth(1978);
		assertEquals(2, person.getId());
		assertEquals("Tom Cruise", person.getName());
		assertEquals(1978, person.getBirth());
	}

}