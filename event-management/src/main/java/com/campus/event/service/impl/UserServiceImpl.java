package com.campus.event.service.impl;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.campus.event.constant.RoleEnum;
import com.campus.event.dto.UserDTO;
import com.campus.event.entity.User;
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
    private final PasswordEncoder passwordEncoder; 

    @Override
    public UserDTO createUser(UserRequest request) {
        log.info("createUser() called: {}", request);
        try {
            User userEntity = mapper.convertValue(request, User.class);
            RoleEnum userRole = RoleEnum.fromRoleId(request.getRoleId());
            userEntity.setUserRole(userRole);
            userEntity.setPassword(passwordEncoder.encode(request.getPassword()));

            User savedUser = userRepository.save(userEntity);
            log.info("User created successfully: {}", savedUser.getEmail());

            return mapToDTO(savedUser);

        } catch (IllegalArgumentException ex) {
            log.error("Invalid user data during mapping or role resolution", ex);
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
    public List<UserDTO> getAllUsers() {
        try {
            log.info("getAllUsers() called");
            List<User> users = userRepository.findAll();
            List<UserDTO> dtos = users.stream()
                    .map(this::mapToDTO)
                    .toList();

            log.info("Returning {} users", dtos.size());
            return dtos;
        } catch (Exception ex) {
            log.error("Error retrieving users", ex);
            throw new GeneralServiceException("Failed to retrieve users");
        }
    }

    @Override
    public UserDTO getUserById(Long id) {
        log.info("getUserById() called with ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID " + id));

        log.info("User found: {}", user.getEmail());
        return mapToDTO(user);
    }

    @Override
    public UserDTO updateUser(Long id, UserRequest updatedUser) {
        try {
            log.info("updateUserById() called for ID: {}", id);
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID " + id));

            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());

            if (updatedUser.getRoleId() != null) {
                user.setUserRole(RoleEnum.fromRoleId(updatedUser.getRoleId()));
            }

            User updated = userRepository.save(user);
            log.info("User updated successfully with ID: {}", id);

            return mapToDTO(updated);

        } catch (DataIntegrityViolationException ex) {
            log.error("Database constraint violation during user update", ex);
            throw new ConflictException("Updated data conflicts with existing records");
        } catch (Exception ex) {
            log.error("Unexpected error during user update", ex);
            throw new GeneralServiceException("User update failed");
        }
    }

    @Override
    public void deleteUser(Long id) {
        log.info("deleteUser() called for ID: {}", id);
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with ID " + id);
        }
        userRepository.deleteById(id);
        log.info("User deleted successfully with ID: {}", id);
    }

    private UserDTO mapToDTO(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getUserRole(),
                user.getCreatedAt()
        );
    }
    
    @Override
    public long countUsers() {
    	log.info("counting users...");
    	long count = userRepository.count();
    	log.info("users count is : {}",count);
    	return count;
    }
    
}
