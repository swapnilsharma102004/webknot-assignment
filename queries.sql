-- Eventzzz Campus Event Management Platform
-- SQL Queries Documentation
-- This file contains the raw SQL queries used for generating reports

-- =====================================================
-- 1. Event Summary Report Query
-- =====================================================
-- Gets total registrations, attendance percentage, and average feedback for a specific event
SELECT 
    COUNT(r.id) as total_registrations,
    CAST(COUNT(CASE WHEN r.attended = true THEN 1 END) AS DOUBLE PRECISION) * 100.0 / COUNT(r.id) as attendance_percentage,
    AVG(CASE WHEN r.feedback_score IS NOT NULL THEN r.feedback_score END) as average_feedback_score
FROM registrations r 
WHERE r.event_id = ?;

-- =====================================================
-- 2. Event Popularity Report Query
-- =====================================================
-- Gets all events ordered by registration count (most popular first)
SELECT 
    e.name as event_name, 
    COUNT(r.id) as registration_count 
FROM events e 
LEFT JOIN registrations r ON e.id = r.event_id 
GROUP BY e.id, e.name 
ORDER BY registration_count DESC;

-- =====================================================
-- 3. Student Participation Report Query
-- =====================================================
-- Gets all events attended by a specific student
SELECT 
    e.name as event_name, 
    e.date as event_date 
FROM registrations r 
JOIN events e ON r.event_id = e.id 
WHERE r.student_id = ? AND r.attended = true 
ORDER BY e.date DESC;

-- =====================================================
-- 4. Top 3 Most Active Students Query
-- =====================================================
-- Gets the top 3 students with the most attended events
SELECT 
    s.name as student_name, 
    COUNT(r.id) as attended_events_count 
FROM students s 
JOIN registrations r ON s.id = r.student_id 
WHERE r.attended = true 
GROUP BY s.id, s.name 
ORDER BY attended_events_count DESC 
LIMIT 3;

-- =====================================================
-- 5. Events by Type Filter Query
-- =====================================================
-- Gets all events, optionally filtered by event type
SELECT 
    e.id,
    e.name,
    e.event_type,
    e.date,
    c.name as college_name
FROM events e 
JOIN colleges c ON e.college_id = c.id 
WHERE (? IS NULL OR e.event_type = ?)
ORDER BY e.date ASC;

-- =====================================================
-- 6. Registration Check Query
-- =====================================================
-- Checks if a student is already registered for an event
SELECT COUNT(*) 
FROM registrations 
WHERE student_id = ? AND event_id = ?;

-- =====================================================
-- 7. Mark Attendance Update Query
-- =====================================================
-- Updates attendance status for a student's event registration
UPDATE registrations 
SET attended = true 
WHERE student_id = ? AND event_id = ?;

-- =====================================================
-- 8. Update Feedback Score Query
-- =====================================================
-- Updates feedback score for a student's event registration
UPDATE registrations 
SET feedback_score = ? 
WHERE student_id = ? AND event_id = ?;

-- =====================================================
-- 9. Get All Colleges Query
-- =====================================================
-- Gets all colleges in the system
SELECT id, name FROM colleges ORDER BY name;

-- =====================================================
-- 10. Get All Students Query
-- =====================================================
-- Gets all students with their college information
SELECT 
    s.id,
    s.name,
    s.email,
    c.name as college_name
FROM students s 
JOIN colleges c ON s.college_id = c.id 
ORDER BY s.name;

-- =====================================================
-- 11. Event Registration Statistics Query
-- =====================================================
-- Gets comprehensive statistics for all events
SELECT 
    e.id,
    e.name,
    e.event_type,
    e.date,
    c.name as college_name,
    COUNT(r.id) as total_registrations,
    COUNT(CASE WHEN r.attended = true THEN 1 END) as total_attendees,
    CASE 
        WHEN COUNT(r.id) > 0 THEN 
            CAST(COUNT(CASE WHEN r.attended = true THEN 1 END) AS DOUBLE PRECISION) * 100.0 / COUNT(r.id)
        ELSE 0 
    END as attendance_percentage,
    AVG(CASE WHEN r.feedback_score IS NOT NULL THEN r.feedback_score END) as avg_feedback
FROM events e 
JOIN colleges c ON e.college_id = c.id 
LEFT JOIN registrations r ON e.id = r.event_id 
GROUP BY e.id, e.name, e.event_type, e.date, c.name 
ORDER BY e.date DESC;

-- =====================================================
-- 12. College-wise Event Statistics Query
-- =====================================================
-- Gets event statistics grouped by college
SELECT 
    c.name as college_name,
    COUNT(DISTINCT e.id) as total_events,
    COUNT(r.id) as total_registrations,
    COUNT(CASE WHEN r.attended = true THEN 1 END) as total_attendees
FROM colleges c 
LEFT JOIN events e ON c.id = e.college_id 
LEFT JOIN registrations r ON e.id = r.event_id 
GROUP BY c.id, c.name 
ORDER BY total_events DESC;

-- =====================================================
-- 13. Student Activity Summary Query
-- =====================================================
-- Gets comprehensive activity summary for all students
SELECT 
    s.id,
    s.name as student_name,
    s.email,
    c.name as college_name,
    COUNT(r.id) as total_registrations,
    COUNT(CASE WHEN r.attended = true THEN 1 END) as events_attended,
    COUNT(CASE WHEN r.feedback_score IS NOT NULL THEN 1 END) as feedback_given,
    AVG(CASE WHEN r.feedback_score IS NOT NULL THEN r.feedback_score END) as avg_feedback_given
FROM students s 
JOIN colleges c ON s.college_id = c.id 
LEFT JOIN registrations r ON s.id = r.student_id 
GROUP BY s.id, s.name, s.email, c.name 
ORDER BY events_attended DESC, total_registrations DESC;

-- =====================================================
-- 14. Event Type Popularity Query
-- =====================================================
-- Gets popularity statistics by event type
SELECT 
    e.event_type,
    COUNT(DISTINCT e.id) as total_events,
    COUNT(r.id) as total_registrations,
    COUNT(CASE WHEN r.attended = true THEN 1 END) as total_attendees,
    CASE 
        WHEN COUNT(r.id) > 0 THEN 
            CAST(COUNT(CASE WHEN r.attended = true THEN 1 END) AS DOUBLE PRECISION) * 100.0 / COUNT(r.id)
        ELSE 0 
    END as avg_attendance_rate
FROM events e 
LEFT JOIN registrations r ON e.id = r.event_id 
GROUP BY e.event_type 
ORDER BY total_registrations DESC;

-- =====================================================
-- 15. Recent Activity Query
-- =====================================================
-- Gets recent registration and attendance activity
SELECT 
    'Registration' as activity_type,
    s.name as student_name,
    e.name as event_name,
    r.registration_time as activity_time
FROM registrations r 
JOIN students s ON r.student_id = s.id 
JOIN events e ON r.event_id = e.id 
WHERE r.registration_time >= CURRENT_DATE - INTERVAL '7 days'

UNION ALL

SELECT 
    'Attendance' as activity_type,
    s.name as student_name,
    e.name as event_name,
    r.registration_time as activity_time
FROM registrations r 
JOIN students s ON r.student_id = s.id 
JOIN events e ON r.event_id = e.id 
WHERE r.attended = true 
AND r.registration_time >= CURRENT_DATE - INTERVAL '7 days'

ORDER BY activity_time DESC 
LIMIT 20;
