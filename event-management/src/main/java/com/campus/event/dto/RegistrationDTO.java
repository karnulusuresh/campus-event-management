package com.campus.event.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegistrationDTO {
    private Long id;
    private Long userId;
    private String userName;       // optional convenience field
    private Long eventId;
    private String eventTitle;     // optional convenience field
    private String status;
    private LocalDateTime registeredAt;
}
