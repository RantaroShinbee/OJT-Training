package com.customers.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncrytedPassword {

	public static String encrytePassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.encode(password);
	}

	public static boolean checkPassword(String password, String passwordEncoder) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.matches(password, passwordEncoder);
	}

}
