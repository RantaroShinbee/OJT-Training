package com.customers.service;

import java.util.Date;

import com.nimbusds.jwt.JWTClaimsSet;

public interface JwtService {
	static final String USER_NAME = "username";
	static final long EXPIRE_TIME = 604800000;
	static final String SECRET_KEY = "11111111111111111111111111111111";

	String generateToken(String userName);

	JWTClaimsSet getClaimsFromToken(String token);

	Date generateExpirationDate();

	Date getExpriceDateFromToken(String token);

	String getUsernameFromToken(String token);

	byte[] generateShareSecret();

	boolean isTokenExpried(String token);

	boolean validateTokenLogin(String token);
}
