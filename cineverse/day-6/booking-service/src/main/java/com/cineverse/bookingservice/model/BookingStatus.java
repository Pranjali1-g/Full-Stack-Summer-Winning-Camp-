package com.cineverse.bookingservice.model;

public enum BookingStatus {
    INITIATED,   // Booking workflow process kicked off
    LOCKED,      // Seats temporarily held in high-speed cache
    CONFIRMED,   // Transaction cleared and tickets permanently secured
    CANCELLED,   // Aborted out manually by user intervention
    EXPIRED      // Checkout window missed by user time-out
}