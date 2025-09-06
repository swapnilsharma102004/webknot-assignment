package com.eventzzz.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FeedbackRequest {
    @NotNull
    private Long studentId;
    
    @NotNull
    @Min(1)
    @Max(5)
    private Integer score;
}
