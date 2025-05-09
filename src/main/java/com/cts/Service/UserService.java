package com.cts.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.Module.Users;
import com.cts.Repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private AuthenticationManager authman; 
	
	@Autowired
	private JWTService service;
	private BCryptPasswordEncoder encrypt=new BCryptPasswordEncoder(12);
	public Users register(Users user) {
		user.setPassword(encrypt.encode(user.getPassword()));
		return repo.save(user);
	}
	
	public String verify(Users user) {
		Authentication authentication=authman.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
		if(authentication.isAuthenticated()) {
			return service.generateToken(user.getUserName());
		}
		else {
			return "Failure";
		}
}
	@PreAuthorize("hasRole('ADMIN')")
	public Users update(Users user) {
		System.out.print(user.getRoles());
		Users temp_user=repo.findByUserName(user.getUserName());
		temp_user.setUserName(user.getUserName());
		temp_user.setPassword(encrypt.encode(user.getPassword()));	
		temp_user.setRoles(user.getRoles());
		repo.save(temp_user);
		return temp_user;
		
	}
}

