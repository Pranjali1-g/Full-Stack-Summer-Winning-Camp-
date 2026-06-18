package com.cineverse.bookingservice.service;

import com.cineverse.bookingservice.model.Booking;
import com.cineverse.bookingservice.model.BookingStatus;
import com.cineverse.bookingservice.model.ShowSeat;
import com.cineverse.bookingservice.repository.BookingRepository;
import com.cineverse.bookingservice.repository.ShowSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Autowired
    private SeatLockService seatLockService;

    @Transactional
    public Booking initiateBooking(String userId, String showId, List<String> seatNumbers) {
        // Concurrency mitigation: Attempt to acquire temporary locks in Redis first
        for (String seatNum : seatNumbers) {
            boolean acquired = seatLockService.lockSeat(showId, seatNum);
            if (!acquired) {
                throw new RuntimeException("Seat " + seatNum + " is currently locked by another customer processing transaction threads.");
            }
        }

        double totalCost = 0.0;
        for (String seatNum : seatNumbers) {
            ShowSeat seat = showSeatRepository.findByShowIdAndSeatNumber(showId, seatNum)
                    .orElseGet(() -> {
                        ShowSeat s = new ShowSeat();
                        s.setShowId(showId);
                        s.setSeatNumber(seatNum);
                        s.setPrice(200.0); // Baseline default configuration value matching assignment data layouts
                        s.setSeatType("REGULAR");
                        return s;
                    });

            if (seat.getStatus() == BookingStatus.CONFIRMED) {
                throw new RuntimeException("Double Booking Guard Triggered: Seat " + seatNum + " has already been purchased.");
            }

            seat.setStatus(BookingStatus.LOCKED);
            showSeatRepository.save(seat);
            totalCost += seat.getPrice();
        }

        Booking booking = new Booking();
        booking.setBookingId(UUID.randomUUID().toString());
        booking.setUserId(userId);
        booking.setShowId(showId);
        booking.setSelectedSeats(seatNumbers);
        booking.setTotalAmount(totalCost);
        booking.setStatus(BookingStatus.INITIATED);

        return bookingRepository.save(booking);
    }
}