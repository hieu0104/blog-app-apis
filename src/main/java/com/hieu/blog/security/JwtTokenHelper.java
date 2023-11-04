package com.hieu.blog.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenHelper {

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
//	private String secret = "jwtTokenKey";
	private String secret="404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
	// retrieve username from jwt token
	public String getUserNameFromToken(String token) {

		return getClaimFromToken(token, Claims::getSubject);

	}

	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);

	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);

		return claimsResolver.apply(claims);
	}

	// for retrieving any information from token we will need secret key
	private Claims getAllClaimsFromToken(String token) {

		return Jwts.parserBuilder()

				.setSigningKey(getSignKey())

				.build()

				.parseClaimsJws(token)

				.getBody();
	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	// check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	// generate token for user
	public String generateToken(UserDetails userDtails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDtails.getUsername());
	}

	// while creating the token
	// 1.Define claims of the token, like issuer, expiration , subject, and the ID
	// 2.Sign the JWT using the HS512 algorithm an secret key
	// 3.According to JWS Compact Serialization , (http//tool.ietf.org
	private String doGenerateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder()

				.setClaims(claims)

				.setSubject(subject)

				.setIssuedAt(new Date(System.currentTimeMillis()))

				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))

				.signWith(getSignKey(), SignatureAlgorithm.HS256)

				.compact();

	}

	// validate token
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUserNameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
