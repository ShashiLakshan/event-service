package com.event_booking.demo.service.impl;

import com.event_booking.demo.dto.EventDto;
import com.event_booking.demo.dto.TicketDto;
import com.event_booking.demo.entity.EventEntity;
import com.event_booking.demo.enums.TicketType;
import com.event_booking.demo.exception.CustomGlobalException;
import com.event_booking.demo.mapper.EventMapper;
import com.event_booking.demo.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    @Captor
    private ArgumentCaptor<EventEntity> eventEntityArgumentCaptor;

    private EventDto eventDto;
    private EventEntity eventEntity;
    private Set<TicketDto> ticketDtos;

    @BeforeEach
    public void setUp() {

        ticketDtos = Set.of(
                TicketDto.builder()
                        .ticketType(TicketType.VIP)
                        .noOfTickets(10)
                        .unitPrice(BigDecimal.valueOf(1000))
                        .build(),

                TicketDto.builder()
                        .ticketType(TicketType.REGULAR)
                        .noOfTickets(20)
                        .unitPrice(BigDecimal.valueOf(100))
                        .build()
        );
        eventDto = EventDto.builder()
                .eventName("Test_Event")
                .eventDate(LocalDateTime.now())
                .eventLocation("Colombo")
                .tickets(ticketDtos)
                .build();

        eventEntity = EventMapper.toEntity(eventDto);
    }

    @Test
    public void whenRequiredAttributesGiven_thenCreateAnEvent() {
        EventEntity eventEntity = EventMapper.toEntity(eventDto);
        when(eventRepository.save(any(EventEntity.class))).thenReturn(eventEntity);

        EventDto createdEvent = eventService.createEvent(eventDto);
        verify(eventRepository).save(eventEntityArgumentCaptor.capture());
        EventEntity capturedEventEntity = eventEntityArgumentCaptor.getValue();

        assertEquals(eventDto.getEventName(), capturedEventEntity.getName());
        assertEquals(eventDto.getEventDate(), capturedEventEntity.getDate());
        assertEquals(eventDto.getEventLocation(), capturedEventEntity.getLocation());

        assertEquals(eventDto.getEventName(), createdEvent.getEventName());
        assertEquals(eventDto.getEventDate(), createdEvent.getEventDate());
        assertEquals(eventDto.getEventLocation(), createdEvent.getEventLocation());
    }

    @Test
    public void whenEventsAreEmpty_thenReturnEmptySet() {
        when(eventRepository.findAll()).thenReturn(List.of());
        assertTrue(eventService.getAllEvents().isEmpty());
    }

    @Test
    public void whenUpdateEventForNonExistingEvent_thenThrowException() {
        when(eventRepository.findById(eventDto.getEventId())).thenReturn(Optional.empty());

        CustomGlobalException exception = assertThrows(CustomGlobalException.class, () -> eventService.updateEvent(eventDto.getEventId(), eventDto));
        assertEquals("EVENT_NOT_FOUND", exception.getCode());
    }

    @Test
    public void whenDeleteEventById_thenEventIsDeleted() {
        when(eventRepository.findById(eventDto.getEventId())).thenReturn(Optional.of(eventEntity));
        doNothing().when(eventRepository).delete(eventEntity);

        eventService.deleteEventById(eventDto.getEventId());
        verify(eventRepository).delete(eventEntity);
    }

}
