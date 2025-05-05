package com.cts.service;
import com.cts.entity.User;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User registerUser(User user) {
		return userRepository.save(user);
	}

	public User login(String email, String password) {
		return userRepository.findByEmail(email).filter(u -> u.getPassword().equals(password))
				.orElseThrow(() -> new ResourceNotFoundException("Invalid email or password"));
	}

	public User updateProfile(Long userId, User updatedUser) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		user.setName(updatedUser.getName());
		user.setShippingAddress(updatedUser.getShippingAddress());
		user.setPaymentDetails(updatedUser.getPaymentDetails());
		return userRepository.save(user);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
}