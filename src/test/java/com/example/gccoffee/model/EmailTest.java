package com.example.gccoffee.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EmailTest {

	@Test
	public void testInvalidEmail() {
		assertThrows(IllegalArgumentException.class, () -> {
			var email = new Email("accccccc");
		});
	}

	@Test
	public void testValidEmail() {
		assertThrows(IllegalArgumentException.class, () -> {
			var email = new Email("hello@gmail.com");
			assertTrue(email.getAddress().equals("hello@gmail.com"));
		});

	}
}