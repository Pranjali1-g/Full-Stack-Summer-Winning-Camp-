package com.cineverse.bookingservice.service;

import com.cineverse.bookingservice.model.Booking;
import com.cineverse.bookingservice.model.BookingStatus;
import com.cineverse.bookingservice.model.ShowSeat;
import com.cineverse.bookingservice.repository.BookingRepository;
import com.cineverse.bookingservice.repository.ShowSeatRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    // Day 8: Inject RabbitTemplate to publish messages
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Transactional
    public Booking initiateBooking(String userId, String showId, List<String> seatNumbers) {
        
        // Day 7: Redis Concurrency Guard
        for (String seatNum : seatNumbers) {
            boolean acquired = seatLockService.lockSeat(showId, seatNum);
            if (!acquired) {
                throw new RuntimeException("Seat " + seatNum + " is currently locked by another customer.");
            }
        }

        double totalCost = 0.0;
        for (String seatNum : seatNumbers) {
            ShowSeat seat = showSeatRepository.findByShowIdAndSeatNumber(showId, seatNum)
                    .orElseGet(() -> {
                        ShowSeat s = new ShowSeat();
                        s.setShowId(showId);
                        s.setSeatNumber(seatNum);
                        s.setPrice(200.0);
                        s.setSeatType("REGULAR");
                        return s;
                    });

            if (seat.getStatus() == BookingStatus.CONFIRMED) {
                throw new RuntimeException("Double Booking Guard Triggered: Seat " + seatNum + " already purchased.");
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

        Booking savedBooking = bookingRepository.save(booking);

        // Day 8: Publish an event payload message asynchronously to RabbitMQ queue
        String message = "BOOKING_INITIATED_EVENT: ID=" + savedBooking.getBookingId() + " User=" + userId;
        rabbitTemplate.convertAndSend("bookingQueue", message);
        System.out.println("🚀 [Producer] Sent message to RabbitMQ: " + message);

        return savedBooking;
    }
}