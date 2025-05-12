package com.cts.controller;
 
import com.cts.dto.LoginRequest;
import com.cts.entity.User;
import com.cts.service.UserService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
 
@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserService userService;
 
	public UserController(UserService userService) {
		this.userService = userService;
	}
 
	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<User> register(@RequestBody User user) {
		return ResponseEntity.ok(userService.registerUser(user));
	}
 
	@PostMapping("/auth/login")
	public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest) {
		String email = loginRequest.getEmail();
		String password = loginRequest.getPassword();
		return ResponseEntity.ok(userService.login(email, password));
	}
 
	@PutMapping("/{id}")
	public ResponseEntity<User> updateProfile(@PathVariable Long id, @RequestBody User user) {
		return ResponseEntity.ok(userService.updateProfile(id, user));
	}
 
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}
}