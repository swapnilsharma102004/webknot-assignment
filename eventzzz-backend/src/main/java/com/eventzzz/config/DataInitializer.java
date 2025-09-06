package com.eventzzz.config;

import com.eventzzz.entity.*;
import com.eventzzz.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    
    private final CollegeRepository collegeRepository;
    private final StudentRepository studentRepository;
    private final EventRepository eventRepository;
    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Create colleges
        College college1 = new College();
        college1.setName("MIT College of Engineering");
        college1 = collegeRepository.save(college1);
        
        College college2 = new College();
        college2.setName("Stanford University");
        college2 = collegeRepository.save(college2);
        
        // Create students
        Student student1 = new Student();
        student1.setName("Alice Johnson");
        student1.setEmail("alice@mit.edu");
        student1.setCollege(college1);
        student1 = studentRepository.save(student1);
        
        Student student2 = new Student();
        student2.setName("Bob Smith");
        student2.setEmail("bob@mit.edu");
        student2.setCollege(college1);
        student2 = studentRepository.save(student2);
        
        Student student3 = new Student();
        student3.setName("Carol Davis");
        student3.setEmail("carol@stanford.edu");
        student3.setCollege(college2);
        student3 = studentRepository.save(student3);
        
        Student student4 = new Student();
        student4.setName("David Wilson");
        student4.setEmail("david@mit.edu");
        student4.setCollege(college1);
        student4 = studentRepository.save(student4);
        
        Student student5 = new Student();
        student5.setName("Eva Brown");
        student5.setEmail("eva@stanford.edu");
        student5.setCollege(college2);
        student5 = studentRepository.save(student5);
        
        // Create events
        Event event1 = new Event();
        event1.setName("Spring Boot Workshop");
        event1.setEventType(EventType.WORKSHOP);
        event1.setDate(LocalDateTime.now().plusDays(7));
        event1.setCollege(college1);
        event1 = eventRepository.save(event1);
        
        Event event2 = new Event();
        event2.setName("Tech Fest 2024");
        event2.setEventType(EventType.FEST);
        event2.setDate(LocalDateTime.now().plusDays(14));
        event2.setCollege(college1);
        event2 = eventRepository.save(event2);
        
        Event event3 = new Event();
        event3.setName("AI/ML Seminar");
        event3.setEventType(EventType.SEMINAR);
        event3.setDate(LocalDateTime.now().plusDays(21));
        event3.setCollege(college2);
        event3 = eventRepository.save(event3);
        
        Event event4 = new Event();
        event4.setName("Cloud Computing Talk");
        event4.setEventType(EventType.TECH_TALK);
        event4.setDate(LocalDateTime.now().plusDays(28));
        event4.setCollege(college2);
        event4 = eventRepository.save(event4);
        
        Event event5 = new Event();
        event5.setName("Hackathon 2024");
        event5.setEventType(EventType.WORKSHOP);
        event5.setDate(LocalDateTime.now().plusDays(35));
        event5.setCollege(college1);
        event5 = eventRepository.save(event5);
        
        // Create sample registrations
        Registration reg1 = new Registration();
        reg1.setStudent(student1);
        reg1.setEvent(event1);
        reg1.setRegistrationTime(LocalDateTime.now().minusDays(1));
        reg1.setAttended(true);
        reg1.setFeedbackScore(5);
        registrationRepository.save(reg1);
        
        Registration reg2 = new Registration();
        reg2.setStudent(student2);
        reg2.setEvent(event1);
        reg2.setRegistrationTime(LocalDateTime.now().minusDays(1));
        reg2.setAttended(true);
        reg2.setFeedbackScore(4);
        registrationRepository.save(reg2);
        
        Registration reg3 = new Registration();
        reg3.setStudent(student1);
        reg3.setEvent(event2);
        reg3.setRegistrationTime(LocalDateTime.now().minusDays(2));
        reg3.setAttended(true);
        reg3.setFeedbackScore(5);
        registrationRepository.save(reg3);
        
        Registration reg4 = new Registration();
        reg4.setStudent(student3);
        reg4.setEvent(event3);
        reg4.setRegistrationTime(LocalDateTime.now().minusDays(1));
        reg4.setAttended(false);
        registrationRepository.save(reg4);
        
        Registration reg5 = new Registration();
        reg5.setStudent(student4);
        reg5.setEvent(event2);
        reg5.setRegistrationTime(LocalDateTime.now().minusDays(3));
        reg5.setAttended(true);
        reg5.setFeedbackScore(3);
        registrationRepository.save(reg5);
        
        Registration reg6 = new Registration();
        reg6.setStudent(student5);
        reg6.setEvent(event4);
        reg6.setRegistrationTime(LocalDateTime.now().minusDays(1));
        reg6.setAttended(false);
        registrationRepository.save(reg6);
        
        Registration reg7 = new Registration();
        reg7.setStudent(student1);
        reg7.setEvent(event3);
        reg7.setRegistrationTime(LocalDateTime.now().minusDays(2));
        reg7.setAttended(true);
        reg7.setFeedbackScore(4);
        registrationRepository.save(reg7);
        
        // Create sample users
        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@eventzzz.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setFullName("System Administrator");
        admin.setRole(Role.ADMIN);
        admin.setCollege(college1);
        userRepository.save(admin);
        
        User student1User = new User();
        student1User.setUsername("alice");
        student1User.setEmail("alice@mit.edu");
        student1User.setPassword(passwordEncoder.encode("alice123"));
        student1User.setFullName("Alice Johnson");
        student1User.setRole(Role.STUDENT);
        student1User.setCollege(college1);
        userRepository.save(student1User);
        
        User student2User = new User();
        student2User.setUsername("bob");
        student2User.setEmail("bob@mit.edu");
        student2User.setPassword(passwordEncoder.encode("bob123"));
        student2User.setFullName("Bob Smith");
        student2User.setRole(Role.STUDENT);
        student2User.setCollege(college1);
        userRepository.save(student2User);
        
        System.out.println("Sample data initialized successfully!");
        System.out.println("Admin Login: username=admin, password=admin123");
        System.out.println("Student Login: username=alice, password=alice123");
        System.out.println("Student Login: username=bob, password=bob123");
    }
}
