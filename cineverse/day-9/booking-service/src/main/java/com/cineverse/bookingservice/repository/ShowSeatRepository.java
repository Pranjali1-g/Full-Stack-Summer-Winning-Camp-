package com.cineverse.bookingservice.repository;

import com.cineverse.bookingservice.model.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
    List<ShowSeat> findByShowId(String showId);
    Optional<ShowSeat> findByShowIdAndSeatNumber(String showId, String seatNumber);
}