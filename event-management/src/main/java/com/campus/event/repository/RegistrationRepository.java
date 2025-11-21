package com.campus.event.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.campus.event.entity.Registration;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
	
	 @Transactional
	void deleteByEventId(Long eventId);
	
    List<Registration> findAllByUser_UserId(Long userId);
    
    List<Registration> findByEventId(Long eventId);
    
    Optional<Registration> findByUser_UserIdAndEventId(Long userId, Long eventId);
    
    boolean existsByUser_UserIdAndEvent_Id(Long userId, Long eventId);
    
    @Query("SELECT COUNT(r) FROM Registration r")
    long countRegistrations();
    
    @Query("SELECT COUNT(r) FROM Registration r WHERE r.status = 'PENDING'")
    long countPendingApprovals();

}
