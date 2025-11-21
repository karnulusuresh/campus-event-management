package com.campus.event.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.campus.event.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	@Query("SELECT COUNT(u) FROM User u")
    long countUsers();
	
	Optional<User> findByEmail(String email);

	Optional<User> findByName(String userName);  
	
}

