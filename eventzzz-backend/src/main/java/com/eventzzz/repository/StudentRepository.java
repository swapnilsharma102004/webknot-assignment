package com.eventzzz.repository;

import com.eventzzz.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    @Query("SELECT s.name, COUNT(r) as attendedEventsCount " +
           "FROM Student s JOIN Registration r ON s.id = r.student.id " +
           "WHERE r.attended = true " +
           "GROUP BY s.id, s.name " +
           "ORDER BY attendedEventsCount DESC " +
           "LIMIT 3")
    List<Object[]> findTop3MostActiveStudents();
}
