package com.eventzzz.service;

import com.eventzzz.dto.AuthResponse;
import com.eventzzz.dto.LoginRequest;
import com.eventzzz.dto.RegisterRequest;
import com.eventzzz.entity.College;
import com.eventzzz.entity.Role;
import com.eventzzz.entity.User;
import com.eventzzz.repository.CollegeRepository;
import com.eventzzz.repository.UserRepository;
import com.eventzzz.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final CollegeRepository collegeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setRole(request.getRole());
        
        if (request.getCollegeId() != null) {
            College college = collegeRepository.findById(request.getCollegeId())
                    .orElseThrow(() -> new RuntimeException("College not found"));
            user.setCollege(college);
        }
        
        userRepository.save(user);
        
        String token = jwtUtil.generateToken(user);
        
        return new AuthResponse(token, user.getUsername(), user.getFullName(), user.getRole(), "Registration successful");
    }
    
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        String token = jwtUtil.generateToken(user);
        
        return new AuthResponse(token, user.getUsername(), user.getFullName(), user.getRole(), "Login successful");
    }
}
