package com.eventzzz.service;

import com.eventzzz.dto.CreateEventRequest;
import com.eventzzz.dto.EventResponse;
import com.eventzzz.entity.College;
import com.eventzzz.entity.Event;
import com.eventzzz.repository.CollegeRepository;
import com.eventzzz.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {
    
    private final EventRepository eventRepository;
    private final CollegeRepository collegeRepository;
    
    public EventResponse createEvent(CreateEventRequest request) {
        College college = collegeRepository.findById(request.getCollegeId())
                .orElseThrow(() -> new RuntimeException("College not found"));
        
        Event event = new Event();
        event.setName(request.getName());
        event.setEventType(request.getEventType());
        event.setDate(request.getDate());
        event.setCollege(college);
        
        event = eventRepository.save(event);
        
        return new EventResponse(
                event.getId(),
                event.getName(),
                event.getEventType(),
                event.getDate(),
                event.getCollege().getName()
        );
    }
    
    public void deleteEvent(UUID eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        eventRepository.delete(event);
    }
    
    public List<EventResponse> getAllEvents() {
        List<Event> events = eventRepository.findAll();
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
