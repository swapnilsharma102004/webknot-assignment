package com.eventzzz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveStudentResponse {
    private String studentName;
    private Long attendedEventsCount;
}
