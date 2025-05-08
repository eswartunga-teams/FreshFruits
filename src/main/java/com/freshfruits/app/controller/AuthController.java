package com.freshfruits.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freshfruits.app.dto.LoginRequest;
import com.freshfruits.app.entity.User;
import com.freshfruits.app.service.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/api/auth")
public class AuthController {

	AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
		try {
			User user = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
			String token = authService.generateToken(user);

			Cookie cookie = new Cookie("authToken", token);
			cookie.setHttpOnly(true);
			cookie.setSecure(false);
			cookie.setPath("/");
			cookie.setMaxAge(3600);
			cookie.setDomain("localhost");

			response.addCookie(cookie);
			response.addHeader("Set-Cookie",
                    "authToken=%s HttpOnly; path=/ ; Max-Age=3600 ; SameSite=None".formatted(token));

			Map<String, String> responseBody = new HashMap<>();
			responseBody.put("message", "Login Successful");
			responseBody.put("username", user.getUsername());
			responseBody.put("role", user.getRole().name());

			return ResponseEntity.ok(responseBody);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("Error", e.getMessage()));
		}
	}
}
