package com.campus.event.service.impl;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.campus.event.entity.User;
import com.campus.event.entity.User.Role;
import com.campus.event.exception.BadRequestException;
import com.campus.event.exception.ConflictException;
import com.campus.event.exception.GeneralServiceException;
import com.campus.event.exception.ResourceNotFoundException;
import com.campus.event.pojo.UserRequest;
import com.campus.event.repository.UserRepository;
import com.campus.event.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService { 

    private final UserRepository userRepository;
    
    private final ObjectMapper mapper;
   
    @Override
    public User createUser(UserRequest request) {
    	try {
    		log.info("createUser() was called: {}",request);
        	User requestObj = mapper.convertValue(request,User.class);
        	requestObj.setRole(Role.STUDENT);
        	User response = userRepository.save(requestObj); 
        	log.info("recieved response from : {}",response); 
        	
            return response;
    	}
    	catch (IllegalArgumentException ex) {
            log.error("Invalid user data during mapping", ex);
            throw new BadRequestException("Invalid input data");
        } catch (DataIntegrityViolationException ex) {
            log.error("Database constraint violation during user creation", ex);
            throw new ConflictException("User data conflicts with existing records");
        } catch (Exception ex) {
            log.error("Unexpected error during user creation", ex);
            throw new GeneralServiceException("User creation failed");
        } 
    }

    @Override
    public List<User> getAllUsers() {  
    	try {
    		log.info("getAllUser() was called");
            List<User> response = userRepository.findAll();  
            log.info("All users are :  {}",response);
            return response; 
    	}
        
        catch (Exception ex) {
            log.error("Error retrieving users", ex);
            throw new GeneralServiceException("Failed to retrieve users");
        }
    }

    @Override
    public User getUserById(Long id) {
    	log.info("getuserById() was called");
        User response = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID " + id));
        log.info("user was found : {}",response);
        return response;
    }

   

    @Override
    public User updateUser(Long id, UserRequest updatedUser) {
    	try {
    		log.info("updateUserById() was called");
            User user = getUserById(id);
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            User response = userRepository.save(user);
            log.info("updated user with id : {}",id);
            return response; 
    	}
        catch (DataIntegrityViolationException ex) {
            log.error("Database constraint violation during user update", ex);
            throw new ConflictException("Updated data conflicts with existing records");
        } catch (Exception ex) {
            log.error("Unexpected error during user update", ex);
            throw new GeneralServiceException("User update failed");
        }
    }

    @Override
    public void deleteUser(Long id)  {
       log.info("deleteUser() called for id: {}", id);
       if (!userRepository.existsById(id)) {
           throw new ResourceNotFoundException("User not found with ID " + id);
       }
        userRepository.deleteById(id);
        log.info("user deleted ");
    }
}
