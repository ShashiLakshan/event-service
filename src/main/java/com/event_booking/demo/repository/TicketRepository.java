package com.event_booking.demo.repository;

import com.event_booking.demo.entity.TicketEntity;
import com.event_booking.demo.enums.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<TicketEntity, Integer> {

    Optional<TicketEntity> findByTicketTypeAndEventId(TicketType ticketType, Integer eventId);

}
