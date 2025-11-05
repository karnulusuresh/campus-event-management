package com.campus.event.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campus.event.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByEmail(String email); 
	
}

