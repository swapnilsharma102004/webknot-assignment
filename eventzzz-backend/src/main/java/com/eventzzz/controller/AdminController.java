package com.eventzzz.controller;

import com.eventzzz.dto.ApiResponse;
import com.eventzzz.dto.CreateEventRequest;
import com.eventzzz.dto.EventResponse;
import com.eventzzz.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    
    private final AdminService adminService;
    
    @PostMapping("/events")
    public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody CreateEventRequest request) {
        EventResponse response = adminService.createEvent(request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<ApiResponse> deleteEvent(@PathVariable UUID eventId) {
        adminService.deleteEvent(eventId);
        return ResponseEntity.ok(ApiResponse.success("Event deleted successfully"));
    }
    
    @GetMapping("/events")
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        List<EventResponse> events = adminService.getAllEvents();
        return ResponseEntity.ok(events);
    }
}
