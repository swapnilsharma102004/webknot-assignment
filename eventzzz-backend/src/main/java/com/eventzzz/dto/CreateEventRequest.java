package com.eventzzz.dto;

import com.eventzzz.entity.EventType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateEventRequest {
    @NotBlank
    private String name;
    
    @NotNull
    private EventType eventType;
    
    @NotNull
    private LocalDateTime date;
    
    @NotNull
    private Long collegeId;
}
