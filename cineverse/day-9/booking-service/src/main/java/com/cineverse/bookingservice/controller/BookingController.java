package com.cineverse.bookingservice.controller;

import com.cineverse.bookingservice.model.Booking;
import com.cineverse.bookingservice.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/initiate")
    public ResponseEntity<Booking> startBooking(
            @RequestParam String userId,
            @RequestParam String showId,
            @RequestParam List<String> seats) {
        return ResponseEntity.ok(bookingService.initiateBooking(userId, showId, seats));
    }
}