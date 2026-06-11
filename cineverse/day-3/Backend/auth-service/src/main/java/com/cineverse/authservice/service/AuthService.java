package com.cineverse.authservice.service;

import com.cineverse.authservice.dto.RegisterDTO;
import com.cineverse.authservice.entity.User;
import com.cineverse.authservice.repository.UserRepository;
import com.cineverse.authservice.exception.UserAlreadyExistsException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Secure cryptographic signing key for JWT
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String registerUser(RegisterDTO dto) {
        // FIXED: Now accurately triggers your GlobalExceptionHandler using the custom business exception
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username already exists!");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        // Hash password before saving it to PostgreSQL
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole().toUpperCase());

        userRepository.save(user);
        return "User registered successfully!";
    }

    public String loginUser(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid username or password!"));

        // Match raw password with hashed database password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid username or password!");
        }

        // Generate Stateless JWT Token
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hour expiry
                .signWith(key)
                .compact();
    }
}