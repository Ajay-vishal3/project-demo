package com.cts.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cts.Module.UserDetail;
import com.cts.Module.Users;
import com.cts.Repository.UserRepo;
@Service
public class MyuserDetailService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Users user=userRepo.findByUserName(username);
		if(user == null) {
			System.out.println("Not an active user");
			throw new UsernameNotFoundException("User not found");
		}
		return new UserDetail(user);
	}

}
