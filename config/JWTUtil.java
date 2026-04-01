package com.ecommerce.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {
	@Value("${jwt.secret}")
	private String secret; 
	
	public String generateToken(String username) {
		if (secret.length() < 32) {
		    throw new RuntimeException("JWT secret must be at least 32 characters");
		}
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() +86400000))
				.signWith(Keys.hmacShaKeyFor(secret.getBytes()))
				.compact();
	}
	
	public String extractUsername(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(secret.getBytes())
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	

}
