package com.campus.event.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EventDTO {
    private Long id;
    
    private String title;
    
    private String description;
    
    private LocalDateTime startDateTime;
    
    private LocalDateTime endDateTime;
    
    private String location;
    
    private String categoryName;
    
    private Long createdByUserId;

    public EventDTO() {}

    public EventDTO(Long id, String title, String description,
                    LocalDateTime startDateTime, LocalDateTime endDateTime,
                    String location, String categoryName, Long createdByUserId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.location = location;
        this.categoryName = categoryName;
        this.createdByUserId = createdByUserId;
    }

}