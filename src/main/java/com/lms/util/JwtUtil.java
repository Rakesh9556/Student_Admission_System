package com.lms.util;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {
	
	// Get the secret from envirnment
	final String secretKey = System.getenv("JWT_SECRET"); 
	private Key secret = Keys.hmacShaKeyFor(secretKey.getBytes());
	
	
	public Key getSecret() {
		return secret;
	}

	public void setSecret(Key secret) {
		this.secret = secret;
	}

	
	// Create Student jwt token
	public String generateStudentAccessToken(String id, String role, String studentType, Map<String, Object> additionalClaims) {
		Date currentTime = new Date(System.currentTimeMillis());
		Date expTime = new Date(System.currentTimeMillis() + 3600000);
		
		return Jwts.builder()
				.setSubject(id)
				.claim("role", role)
				.claim("studentType", studentType)
				.addClaims(additionalClaims)
				.setIssuedAt(currentTime)
				.setExpiration(expTime)
				.signWith(secret, SignatureAlgorithm.HS256)
				.compact();
	}
	
	// Create faculty jwt token
		public String generateFacultyAccessToken(String email, String role, String facultyId, String department) {
			Date currentTime = new Date(System.currentTimeMillis());
			Date expTime = new Date(System.currentTimeMillis() + 3600000);
			
			return Jwts.builder()
					.setSubject(email)
					.claim("role", role)
					.claim("facultyId", facultyId)
					.claim("department", department)
//					.claim("permissions", permissions)
					.setIssuedAt(currentTime)
					.setExpiration(expTime)
					.signWith(secret, SignatureAlgorithm.HS256)
					.compact();
		}

	
		// Create admin jwt token
		public String generateAdminAccessToken(String email, String role, String adminId, String adminLevel) {
			Date currentTime = new Date(System.currentTimeMillis());
			Date expTime = new Date(System.currentTimeMillis() + 3600000);
			
			return Jwts.builder()
					.setSubject(email)
					.claim("role", role)
					.claim("adminId", adminId)
					.claim("adminLevel", adminLevel)
//					.claim("permissions", permissions)
					.setIssuedAt(currentTime)
					.setExpiration(expTime)
					.signWith(secret, SignatureAlgorithm.HS256)
					.compact();
		}
	
	
	// generate refresh token
	public String generateRefreshToken(String id) {
		Date currentTime = new Date(System.currentTimeMillis());
		Date expTime = new Date(System.currentTimeMillis() + 2592000000L); // 30 day expiry
		
		return Jwts.builder()
				.setSubject(id)
				.setIssuedAt(currentTime)
				.setExpiration(expTime)
				.signWith(secret, SignatureAlgorithm.HS256)
				.compact();
	}
	
	// Parse token for retriving user related info
	public Claims parseToken(String token) {
		try {
			return Jwts.parserBuilder()
					.setSigningKey(getSecret())
					.build()
					.parseClaimsJws(token.replace("Bearer ", ""))
					.getBody();
        } catch (Exception e) {
            throw new ApiError(400, "Failed to parse token");
        }
	}
	
	// retrive user id from the token
	public String getUserIdFromToken(String token) {
		return parseToken(token).getSubject();
	}
	
	// Retrive user role from token
	public String getUserRoleFromToken(String token) {
		return parseToken(token).get("role", String.class);
	}
	
	// Validate the token expiry
	public void validateTokenExpiry(String token) {
		Date expiration = parseToken(token).getExpiration();
		if(expiration != null && expiration.before(new Date())) {
			throw new ApiError(401, "Token has expired!");
		}
	}
	

}
