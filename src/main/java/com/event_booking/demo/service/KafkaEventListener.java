package com.event_booking.demo.service;


import com.event_booking.demo.dto.PaymentEventDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaEventListener {

    private final TicketService ticketService;

    @KafkaListener(topics = "event-topic", groupId = "event-group")
    public void consumePaymentEvent(PaymentEventDto paymentEventDto) {
        log.info("Payment event consumed: {}", paymentEventDto);
        ticketService.updateTicketDetails(paymentEventDto);
    }
}
