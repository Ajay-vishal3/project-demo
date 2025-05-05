package com.cts.controller;

import com.cts.entity.Admin;
import com.cts.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
	private final AdminService adminService;

	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}

	@PostMapping
	public ResponseEntity<Admin> create(@RequestBody Admin admin) {
		return ResponseEntity.ok(adminService.createAdmin(admin));
	}

	@GetMapping
	public ResponseEntity<List<Admin>> getAll() {
		return ResponseEntity.ok(adminService.getAllAdmins());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Admin> update(@PathVariable Long id, @RequestBody Admin admin) {
		return ResponseEntity.ok(adminService.updateAdmin(id, admin));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		adminService.deleteAdmin(id);
		return ResponseEntity.noContent().build();
	}
}
