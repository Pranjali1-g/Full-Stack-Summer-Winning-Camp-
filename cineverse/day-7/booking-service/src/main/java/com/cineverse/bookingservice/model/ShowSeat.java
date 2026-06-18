package com.cineverse.bookingservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "show_seats")
@Data
public class ShowSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String showId;     // Maps back to targeted show
    private String seatNumber; // Formats grid matrix mapping (e.g., "A1", "A2")
    private String seatType;   // REGULAR, PREMIUM, VIP
    private Double price;      // Dynamic tier pricing value assignment

    @Enumerated(EnumType.STRING)
    private BookingStatus status = BookingStatus.INITIATED; 
}