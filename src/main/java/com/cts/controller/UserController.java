package com.cts.controller;

import com.cts.dto.LoginRequest;
import com.cts.entity.User;
import com.cts.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private  UserService userService;


	@PostMapping("/register")
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
