package com.cineverse.bookingservice.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

    public static final String MAIN_QUEUE = "bookingQueue";
    public static final String DLX_EXCHANGE = "bookingDLX";
    public static final String DLQ_QUEUE = "bookingDLQ";

    // 1. Declare the Main Queue and route failures to the DLX
    @Bean
    public Queue bookingQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", DLX_EXCHANGE);
        args.put("x-dead-letter-routing-key", "deadLetter");
        return new Queue(MAIN_QUEUE, true, false, false, args);
    }

    // 2. Declare the Dead Letter Exchange
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DLX_EXCHANGE);
    }

    // 3. Declare the Dead Letter Queue (DLQ)
    @Bean
    public Queue deadLetterQueue() {
        return new Queue(DLQ_QUEUE, true);
    }

    // 4. Bind DLQ to DLX
    @Bean
    public Binding dlqBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with("deadLetter");
    }
}