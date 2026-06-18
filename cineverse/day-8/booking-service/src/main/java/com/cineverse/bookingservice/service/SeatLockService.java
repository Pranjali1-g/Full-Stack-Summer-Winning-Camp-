package com.cineverse.bookingservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;

@Service
public class SeatLockService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public boolean lockSeat(String showId, String seatNumber) {
        String key = "seat:" + showId + ":" + seatNumber;
        Boolean success = redisTemplate.opsForValue()
                .setIfAbsent(key, "LOCKED", Duration.ofMinutes(5)); // 5 minute TTL lock [cite: 113]
        return Boolean.TRUE.equals(success);
    }

    public void releaseSeat(String showId, String seatNumber) {
        String key = "seat:" + showId + ":" + seatNumber;
        redisTemplate.delete(key);
    }
}