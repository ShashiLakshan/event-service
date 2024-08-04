package com.event_service.demo.service;

import com.event_service.demo.dto.EventDto;
import com.event_service.demo.marker_interfaces.CreateMarker;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface EventService {

    @Validated(CreateMarker.class)
    EventDto createEvent(@Valid EventDto event);

    List<EventDto> getAllEvents();

    EventDto getEventById(Integer id);

    List<EventDto> searchEvents(EventDto eventDto);

    EventDto updateEvent(Integer id, EventDto event);

    void deleteEventById(Integer id);
}
