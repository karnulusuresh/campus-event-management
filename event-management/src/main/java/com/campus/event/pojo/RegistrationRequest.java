package com.campus.event.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegistrationRequest {
    @NotNull(message = "userId is required")
    private Long userId;

    @NotNull(message = "eventId is required")
    private Long eventId;
}
