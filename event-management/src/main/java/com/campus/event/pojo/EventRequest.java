package com.campus.event.pojo;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EventRequest {

    private String title;

    private String description;

    private LocalDateTime eventDate;

    private String location;

    private Long categoryId;
}
