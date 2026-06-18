package com.cineverse.bookingservice.repository;

import com.cineverse.bookingservice.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, String> {
}