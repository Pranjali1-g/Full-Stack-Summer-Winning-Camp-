package com.cineverse.authservice.controller;

import com.cineverse.authservice.dto.RegisterDTO;
import com.cineverse.authservice.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*") // Allows your frontend application to contact this service
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDTO dto) {
        return ResponseEntity.ok(authService.registerUser(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String token = authService.loginUser(request.get("username"), request.get("password"));
        return ResponseEntity.ok(Map.of("token", token));
    }
}