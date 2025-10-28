package com.campus.event.pojo;

import lombok.Data;

@Data
public class UserRequest {

    private String name;

    private String email;

    private String password;

    private String role; // e.g., STUDENT / ADMIN
}
