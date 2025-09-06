package com.eventzzz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentParticipationResponse {
    private String eventName;
    private LocalDateTime eventDate;
}
