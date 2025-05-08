//package com.freshfruits.app.controller;
//
//import java.util.Map;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.freshfruits.app.entity.User;
//import com.freshfruits.app.service.UserService;
//
//@RestController
//@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
//@RequestMapping("/api/users")
//public class UserController {
//
//	UserService userService;
//	public UserController(UserService userService) {
//		this.userService = userService;
//	}
//
//	@PostMapping("/register")
//	public ResponseEntity<?> registerUser(@RequestBody User user) {
//		try {
//			User ruser = userService.register(user);
//			return ResponseEntity.ok(Map.of("message", "User Registered Successfully", "username", ruser.getUsername()));
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
//		}
//	}
//}
