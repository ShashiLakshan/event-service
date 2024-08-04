package com.event_service.demo.repository;

import com.event_service.demo.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<TicketEntity, Integer> {

}
