package com.eventzzz.service;

import com.eventzzz.dto.*;
import com.eventzzz.entity.Event;
import com.eventzzz.entity.EventType;
import com.eventzzz.entity.Registration;
import com.eventzzz.entity.Student;
import com.eventzzz.repository.EventRepository;
import com.eventzzz.repository.RegistrationRepository;
import com.eventzzz.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {
    
    private final EventRepository eventRepository;
    private final StudentRepository studentRepository;
    private final RegistrationRepository registrationRepository;
    
    public ApiResponse registerStudentForEvent(UUID eventId, RegistrationRequest request) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        // Check if already registered
        if (registrationRepository.findByStudentIdAndEventId(request.getStudentId(), eventId).isPresent()) {
            return ApiResponse.error("Student is already registered for this event");
        }
        
        Registration registration = new Registration();
        registration.setStudent(student);
        registration.setEvent(event);
        registration.setRegistrationTime(LocalDateTime.now());
        registration.setAttended(false);
        
        registrationRepository.save(registration);
        
        return ApiResponse.success("Student registered successfully for the event");
    }
    
    public ApiResponse markAttendance(UUID eventId, AttendanceRequest request) {
        Registration registration = registrationRepository.findByStudentIdAndEventId(request.getStudentId(), eventId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        
        int updated = registrationRepository.markAttendance(request.getStudentId(), eventId);
        
        if (updated > 0) {
            return ApiResponse.success("Attendance marked successfully");
        } else {
            return ApiResponse.error("Failed to mark attendance");
        }
    }
    
    public ApiResponse submitFeedback(UUID eventId, FeedbackRequest request) {
        Registration registration = registrationRepository.findByStudentIdAndEventId(request.getStudentId(), eventId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        
        if (!registration.getAttended()) {
            return ApiResponse.error("Cannot submit feedback without attending the event");
        }
        
        int updated = registrationRepository.updateFeedbackScore(request.getStudentId(), eventId, request.getScore());
        
        if (updated > 0) {
            return ApiResponse.success("Feedback submitted successfully");
        } else {
            return ApiResponse.error("Failed to submit feedback");
        }
    }
    
    public EventSummaryResponse getEventSummary(UUID eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        
        Object[] result = registrationRepository.getEventSummary(eventId);
        
        Long totalRegistrations = ((Number) result[0]).longValue();
        Double attendancePercentage = result[1] != null ? ((Number) result[1]).doubleValue() : 0.0;
        Double averageFeedbackScore = result[2] != null ? ((Number) result[2]).doubleValue() : null;
        
        return new EventSummaryResponse(totalRegistrations, attendancePercentage, averageFeedbackScore);
    }
    
    public List<EventPopularityResponse> getEventPopularity() {
        List<Object[]> results = eventRepository.findEventsOrderedByPopularity();
        
        return results.stream()
                .map(result -> new EventPopularityResponse(
                        (String) result[0],
                        ((Number) result[1]).longValue()
                ))
                .collect(Collectors.toList());
    }
    
    public List<StudentParticipationResponse> getStudentParticipation(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        List<Object[]> results = registrationRepository.findAttendedEventsByStudentId(studentId);
        
        return results.stream()
                .map(result -> new StudentParticipationResponse(
                        (String) result[0],
                        (LocalDateTime) result[1]
                ))
                .collect(Collectors.toList());
    }
    
    public List<ActiveStudentResponse> getActiveStudents() {
        List<Object[]> results = studentRepository.findTop3MostActiveStudents();
        
        return results.stream()
                .map(result -> new ActiveStudentResponse(
                        (String) result[0],
                        ((Number) result[1]).longValue()
                ))
                .collect(Collectors.toList());
    }
    
    public List<EventResponse> getEvents(EventType eventType) {
        List<Event> events = eventRepository.findEventsByType(eventType);
        
        return events.stream()
                .map(event -> new EventResponse(
                        event.getId(),
                        event.getName(),
                        event.getEventType(),
                        event.getDate(),
                        event.getCollege().getName()
                ))
                .collect(Collectors.toList());
    }
}
