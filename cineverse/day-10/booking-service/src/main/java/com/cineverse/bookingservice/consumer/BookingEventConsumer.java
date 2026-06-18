package com.cineverse.bookingservice.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class BookingEventConsumer {

    @RabbitListener(queues = "bookingQueue")
    public void receiveBookingEvent(String message) {
        System.out.println("📥 [Consumer] Received message from RabbitMQ: " + message);
        // Cleaned up! Ready for normal processing.
    }
}