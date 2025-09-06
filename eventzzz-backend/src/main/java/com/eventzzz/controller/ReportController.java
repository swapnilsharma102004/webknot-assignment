package com.eventzzz.controller;

import com.eventzzz.dto.*;
import com.eventzzz.entity.EventType;
import com.eventzzz.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ReportController {
    
    private final EventService eventService;
    
    @GetMapping("/events/{eventId}/summary")
    public ResponseEntity<EventSummaryResponse> getEventSummary(@PathVariable UUID eventId) {
        EventSummaryResponse response = eventService.getEventSummary(eventId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/popularity")
    public ResponseEntity<List<EventPopularityResponse>> getEventPopularity() {
        List<EventPopularityResponse> response = eventService.getEventPopularity();
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/students/{studentId}/participation")
    public ResponseEntity<List<StudentParticipationResponse>> getStudentParticipation(@PathVariable Long studentId) {
        List<StudentParticipationResponse> response = eventService.getStudentParticipation(studentId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/active-students")
    public ResponseEntity<List<ActiveStudentResponse>> getActiveStudents() {
        List<ActiveStudentResponse> response = eventService.getActiveStudents();
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/events")
    public ResponseEntity<List<EventResponse>> getEvents(@RequestParam(required = false) EventType type) {
        List<EventResponse> response = eventService.getEvents(type);
        return ResponseEntity.ok(response);
    }
}
