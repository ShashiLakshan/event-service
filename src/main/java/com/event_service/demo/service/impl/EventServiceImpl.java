package com.event_service.demo.service.impl;

import com.event_service.demo.dto.EventDto;
import com.event_service.demo.entity.EventEntity;
import com.event_service.demo.exception.CustomGlobalException;
import com.event_service.demo.mapper.EventMapper;
import com.event_service.demo.repository.EventRepository;
import com.event_service.demo.repository.TicketRepository;
import com.event_service.demo.service.EventService;
import com.event_service.demo.specification.EventSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;

    @Transactional
    @Override
    public EventDto createEvent(EventDto event) {
        EventEntity eventEntity = eventRepository.save(EventMapper.toEntity(event));
        return EventMapper.toDto(eventEntity);
    }

    @Override
    public List<EventDto> getAllEvents() {
        return eventRepository.findAll().stream().map(EventMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public EventDto getEventById(Integer id) {
        return eventRepository.findById(id)
                .map(EventMapper::toDto)
                .orElseThrow(() -> new CustomGlobalException("EVENT_NOT_FOUND", "Event not found", HttpStatus.NOT_FOUND));
    }

    public List<EventDto> searchEvents(EventDto eventDto) {
        Specification<EventEntity> specification = Specification.where(EventSpecification.hasName(eventDto.getEventName()))
                .and(EventSpecification.hasLocation(eventDto.getEventLocation()))
                .and(EventSpecification.hasDate(eventDto.getEventDate()));
        return eventRepository.findAll(specification).stream().map(EventMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventDto updateEvent(Integer id, EventDto eventDto) {

        EventEntity eventEntity = eventRepository.findById(id)
                .orElseThrow(() -> new CustomGlobalException("EVENT_NOT_FOUND", "Event not found", HttpStatus.NOT_FOUND));

        EventMapper.toUpdateEntity(eventDto, eventEntity);
        EventEntity updateEntity = eventRepository.save(eventEntity);
        return EventMapper.toDto(updateEntity);
    }

    @Override
    @Transactional
    public void deleteEventById(Integer id) {
        EventEntity eventEntity = eventRepository.findById(id)
                .orElseThrow(() -> new CustomGlobalException("EVENT_NOT_FOUND", "Event not found", HttpStatus.NOT_FOUND));
        eventRepository.delete(eventEntity);
    }
}
