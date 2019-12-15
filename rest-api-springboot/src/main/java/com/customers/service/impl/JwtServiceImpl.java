package com.customers.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.customers.service.JwtService;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Service
public class JwtServiceImpl implements JwtService {

	@Override
	public String generateToken(String userName) {
		String token = null;
		try {
			JWSSigner signer = new MACSigner(generateShareSecret());
			JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
			builder.claim(USER_NAME, userName);
			builder.expirationTime(generateExpirationDate());
			JWTClaimsSet claimsSet = builder.build();
			SignedJWT signnerJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
			signnerJWT.sign(signer);
			token = signnerJWT.serialize();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return token;
	}

	@Override
	public JWTClaimsSet getClaimsFromToken(String token) {
		JWTClaimsSet claims = null;
		try {
			SignedJWT signedJWT = SignedJWT.parse(token);
			JWSVerifier verifier = new MACVerifier(generateShareSecret());
			if (signedJWT.verify(verifier)) {
				claims = signedJWT.getJWTClaimsSet();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return claims;
	}

	@Override
	public Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + EXPIRE_TIME);
	}

	@Override
	public Date getExpriceDateFromToken(String token) {
		Date exprication = null;
		JWTClaimsSet claims = getClaimsFromToken(token);
		exprication = claims.getExpirationTime();
		
		return exprication;
	}

	@Override
	public String getUsernameFromToken(String token) {
		String userName = null;
		try {
			JWTClaimsSet claimsSet = getClaimsFromToken(token);
			userName = claimsSet.getStringClaim(USER_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return userName;
	}

	@Override
	public byte[] generateShareSecret() {
		byte[] shareSecret = new byte[32];
		shareSecret = SECRET_KEY.getBytes();
		
		return shareSecret;
	}

	@Override
	public boolean isTokenExpried(String token) {
		Date expration = getExpriceDateFromToken(token);
		
		return expration.before(new Date());
	}

	@Override
	public boolean validateTokenLogin(String token) {
		if (token == null || token.trim().length() == 0) {
			return false;
		}
		String userName = getUsernameFromToken(token);
		if (userName == null || userName.isEmpty()) {
			return false;
		}
		if (isTokenExpried(token)) {
			return false;
		}
		
		return true;
	}
}
