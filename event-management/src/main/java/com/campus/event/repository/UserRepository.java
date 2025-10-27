package com.campus.event.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.campus.event.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);
	
}

