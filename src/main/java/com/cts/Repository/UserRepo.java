package com.cts.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.Module.Users;
@Repository
public interface UserRepo extends JpaRepository<Users,Integer> {

	Users findByUserName(String userName);

}
