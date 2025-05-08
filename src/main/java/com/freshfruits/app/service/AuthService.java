package com.freshfruits.app.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.freshfruits.app.entity.JWTToken;
import com.freshfruits.app.entity.User;
import com.freshfruits.app.repository.JWTTokenRepository;
import com.freshfruits.app.repository.UserRepository;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthService {

	private final Key SIGNING_KEY;
	UserRepository userRepository;
	JWTTokenRepository jwtTokenRepository;
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public AuthService(UserRepository userRepository, JWTTokenRepository jwtTokenRepository,
			@Value("${jwt.secret}") String jwtSecret) {
		this.userRepository = userRepository;
		this.jwtTokenRepository = jwtTokenRepository;
		this.SIGNING_KEY = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
	}

	public User authenticate(String username, String password) {
		// step-1 using username fetch User obj from userrepository
		Optional<User> existingUser = userRepository.findByUsername(username);
		if (existingUser.isPresent()) {
			User user = existingUser.get();
			// step-2 check the password by decripting (use BCryptPasswordEncoders match())
			if (!passwordEncoder.matches(password, user.getPassword())) {
				throw new RuntimeException("Invalid Password");
			}
			// step-5 return user
			return user;
		} else {
			throw new RuntimeException("Invalid Username");
		}
	}

	public String generateToken(User user) {
		String token;
		LocalDateTime currentTime = LocalDateTime.now();
		JWTToken existingToken = jwtTokenRepository.findByUser_UserId(user.getUserId());
		if (existingToken != null && currentTime.isBefore(existingToken.getExpairesAt())) {
			token = existingToken.getToken();
		} else {
			token = generateNewToken(user);
			if (existingToken != null) {
				jwtTokenRepository.delete(existingToken);
			}
			saveToken(user, token);
		}
		return token;
	}

	public String generateNewToken(User user) {
		JwtBuilder builder = Jwts.builder();
		builder.setSubject(user.getUsername());
		builder.claim("role", user.getRole().name());
		builder.setIssuedAt(new Date());
		builder.setExpiration(new Date(System.currentTimeMillis() + 3600000));
		builder.signWith(SIGNING_KEY);
		String token = builder.compact();
		return token;
	}

	public void saveToken(User user, String token) {
		JWTToken jwtToken = new JWTToken(user, token, LocalDateTime.now(), LocalDateTime.now().plusHours(1));
		jwtTokenRepository.save(jwtToken);
	}

	public boolean validateToken(String token) {

		System.out.println("Validating token");
		try {
			// parse and validate token

			Jwts.parserBuilder()
			.setSigningKey(SIGNING_KEY)
			.build()
			.parseClaimsJws(token);

			// check if token present in DB and is not expaired
			Optional<JWTToken> jwtToken = jwtTokenRepository.findByToken(token);
			if (jwtToken.isPresent()) {
				return jwtToken.get().getExpairesAt().isAfter(LocalDateTime.now());
			}
			return false;
		} catch (Exception e) {
			System.out.println("Token Validation Failed" + e.getMessage());
			return false;
		}
	}

	public String extractUsername(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(SIGNING_KEY)
				.build().parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
}
