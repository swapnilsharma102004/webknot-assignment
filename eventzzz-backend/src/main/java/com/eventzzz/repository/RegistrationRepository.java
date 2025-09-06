package com.eventzzz.repository;

import com.eventzzz.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    
    Optional<Registration> findByStudentIdAndEventId(Long studentId, UUID eventId);
    
    @Query("SELECT COUNT(r), " +
           "CAST(COUNT(CASE WHEN r.attended = true THEN 1 END) AS double) * 100.0 / COUNT(r), " +
           "AVG(CASE WHEN r.feedbackScore IS NOT NULL THEN r.feedbackScore END) " +
           "FROM Registration r WHERE r.event.id = :eventId")
    Object[] getEventSummary(@Param("eventId") UUID eventId);
    
    @Query("SELECT e.name, e.date " +
           "FROM Registration r JOIN r.event e " +
           "WHERE r.student.id = :studentId AND r.attended = true " +
           "ORDER BY e.date DESC")
    List<Object[]> findAttendedEventsByStudentId(@Param("studentId") Long studentId);
    
    @Modifying
    @Query("UPDATE Registration r SET r.attended = true WHERE r.student.id = :studentId AND r.event.id = :eventId")
    int markAttendance(@Param("studentId") Long studentId, @Param("eventId") UUID eventId);
    
    @Modifying
    @Query("UPDATE Registration r SET r.feedbackScore = :score WHERE r.student.id = :studentId AND r.event.id = :eventId")
    int updateFeedbackScore(@Param("studentId") Long studentId, @Param("eventId") UUID eventId, @Param("score") Integer score);
}
