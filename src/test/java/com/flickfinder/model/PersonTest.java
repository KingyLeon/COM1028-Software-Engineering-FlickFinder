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

	/**
	 * The person object to be tested.
	 */
	private Person person;

	/**
	 * Set up the person object before each test.
	 *
	 */
	@BeforeEach
	public void setUp() {
		person = new Person(1, "Leon King", 2004);
	}

	/**
	 * Test the person object is created with the correct values.
	 */
	@Test
	public void testPersonCreated() {
		assertEquals(1, person.getId());
		assertEquals("Leon King", person.getName());
		assertEquals(2004, person.getBirth());
	}

	/**
	 * Test the person object is created with the correct values.
	 */
	@Test
	public void testPersonSetters() {
		person.setId(2);
		person.setName("Joe Appleton");
		person.setBirth(1980);
		assertEquals(2, person.getId());
		assertEquals("Joe Appleton", person.getName());
		assertEquals(1980, person.getBirth());
	}
}