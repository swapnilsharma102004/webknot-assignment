package com.eventzzz.repository;

import com.eventzzz.entity.Event;
import com.eventzzz.entity.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    
    @Query("SELECT e.name, COUNT(r) as registrationCount " +
           "FROM Event e LEFT JOIN Registration r ON e.id = r.event.id " +
           "GROUP BY e.id, e.name " +
           "ORDER BY registrationCount DESC")
    List<Object[]> findEventsOrderedByPopularity();
    
    @Query("SELECT e FROM Event e WHERE (:eventType IS NULL OR e.eventType = :eventType)")
    List<Event> findEventsByType(@Param("eventType") EventType eventType);
}
