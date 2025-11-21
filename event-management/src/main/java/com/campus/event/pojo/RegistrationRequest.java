package com.campus.event.pojo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegistrationRequest {
	
    @NotNull(message = "userName is required")
    private String name;
    
    @NotNull(message = "email is required")
    @Email
    private String email;

    @NotNull(message = "eventName is required")
    private String title;
}
