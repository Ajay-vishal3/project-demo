package com.cts.service;

import com.cts.entity.Admin;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.AdminRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminService {
	private final AdminRepository adminRepository;

	public AdminService(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}

	public Admin createAdmin(Admin admin) {
		return adminRepository.save(admin);
	}

	public List<Admin> getAllAdmins() {
		//return adminRepository.findAll();
		return adminRepository.findAll();
	}

	public Admin updateAdmin(Long id, Admin updatedAdmin) {
		Admin admin = adminRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Admin not found"));
		admin.setName(updatedAdmin.getName());
		admin.setRole(updatedAdmin.getRole());
		admin.setPermissions(updatedAdmin.getPermissions());
		return adminRepository.save(admin);
	}

	public void deleteAdmin(Long id) {
		Admin admin = adminRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Admin not found"));
		adminRepository.delete(admin);
	}
}