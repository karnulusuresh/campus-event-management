package com.campus.event.dto;

import com.campus.event.constant.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long userId;
    private String name;
    private String email;
    private RoleEnum userRole;
    private LocalDateTime createdAt;
}
