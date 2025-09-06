package com.eventzzz.controller;

import com.eventzzz.dto.*;
import com.eventzzz.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class EventController {
    
    private final EventService eventService;
    
    @PostMapping("/{eventId}/register")
    public ResponseEntity<ApiResponse> registerForEvent(
            @PathVariable UUID eventId,
            @Valid @RequestBody RegistrationRequest request) {
        ApiResponse response = eventService.registerStudentForEvent(eventId, request);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/{eventId}/attendance")
    public ResponseEntity<ApiResponse> markAttendance(
            @PathVariable UUID eventId,
            @Valid @RequestBody AttendanceRequest request) {
        ApiResponse response = eventService.markAttendance(eventId, request);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/{eventId}/feedback")
    public ResponseEntity<ApiResponse> submitFeedback(
            @PathVariable UUID eventId,
            @Valid @RequestBody FeedbackRequest request) {
        ApiResponse response = eventService.submitFeedback(eventId, request);
        return ResponseEntity.ok(response);
    }
}
