package com.cineverse.bookingservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "bookings")
@Data
public class Booking {
    @Id
    private String bookingId; // Unique token string
    private String userId;
    private String showId;     // Associated schedule reference
    
    @ElementCollection
    private List<String> selectedSeats; // Selected collection targets array
    
    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    private BookingStatus status; // FSM monitoring marker tracking variable
}