package com.campus.event.pojo;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Start time is required")
    @Future(message = "Start time must be in the future")
    private LocalDateTime startDateTime;

    @NotNull(message = "End time is required")
    @Future(message = "End time must be in the future")
    private LocalDateTime endDateTime;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "CreatedByUserId is required")
    private Long createdByUserId;

   
}

