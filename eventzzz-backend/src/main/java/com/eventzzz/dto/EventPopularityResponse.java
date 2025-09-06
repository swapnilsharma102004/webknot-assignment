package com.eventzzz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventPopularityResponse {
    private String eventName;
    private Long registrationCount;
}
