package com.cineverse.bookingservice;

import com.cineverse.bookingservice.model.Booking;
import com.cineverse.bookingservice.model.BookingStatus;
import com.cineverse.bookingservice.model.ShowSeat;
import com.cineverse.bookingservice.repository.BookingRepository;
import com.cineverse.bookingservice.repository.ShowSeatRepository;
import com.cineverse.bookingservice.service.BookingService;
import com.cineverse.bookingservice.service.SeatLockService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ShowSeatRepository showSeatRepository;

    @Mock
    private SeatLockService seatLockService;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private BookingService bookingService;

    @Test
    void testInitiateBooking_Success() {
        // 1. Arrange
        String userId = "user123";
        String showId = "show456";
        List<String> seats = List.of("A1", "A2");

        when(seatLockService.lockSeat(any(), any())).thenReturn(true);

        ShowSeat mockSeat = new ShowSeat();
        mockSeat.setShowId(showId);
        mockSeat.setPrice(200.0);
        mockSeat.setStatus(BookingStatus.INITIATED); 
        when(showSeatRepository.findByShowIdAndSeatNumber(any(), any())).thenReturn(Optional.of(mockSeat));

        when(bookingRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // 2. Act
        Booking result = bookingService.initiateBooking(userId, showId, seats);

        // 3. Assert
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals(showId, result.getShowId());
        assertEquals(400.0, result.getTotalAmount());
        
        // Explicitly casting or specifying class types prevents method overloading conflicts
        verify(rabbitTemplate, times(1)).convertAndSend(any(String.class), any(Object.class));
    }
}