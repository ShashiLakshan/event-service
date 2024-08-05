package com.event_booking.demo.service.impl;

import com.event_booking.demo.dto.PaymentEventDto;
import com.event_booking.demo.entity.TicketEntity;
import com.event_booking.demo.exception.CustomGlobalException;
import com.event_booking.demo.repository.TicketRepository;
import com.event_booking.demo.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Override
    public void updateTicketDetails(PaymentEventDto paymentEventDto) {
        TicketEntity ticketEntity = ticketRepository.findByTicketTypeAndEventId(paymentEventDto.getTicketType(), paymentEventDto.getEventId())
                .orElseThrow(() -> new CustomGlobalException("EVENT_NOT_FOUND", "Event not found", HttpStatus.NOT_FOUND));
        ticketEntity.setNoOfTickets(ticketEntity.getNoOfTickets() - paymentEventDto.getNoOfTickets());
        ticketRepository.save(ticketEntity);
    }
}
