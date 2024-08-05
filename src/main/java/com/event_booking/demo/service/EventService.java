package com.event_booking.demo.service;

import com.event_booking.demo.dto.EventDto;
import com.event_booking.demo.marker_interfaces.CreateMarker;
import com.event_booking.demo.marker_interfaces.UpdateMarker;
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

    @Validated(UpdateMarker.class)
    EventDto updateEvent(@Valid EventDto event);

    void deleteEventById(Integer id);
}
