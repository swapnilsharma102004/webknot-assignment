package com.eventzzz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventSummaryResponse {
    private Long totalRegistrations;
    private Double attendancePercentage;
    private Double averageFeedbackScore;
}
