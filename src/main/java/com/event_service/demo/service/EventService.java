package com.event_service.demo.service;

import com.event_service.demo.dto.EventDto;
import com.event_service.demo.marker_interfaces.CreateMarker;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface EventService {

    @Validated(CreateMarker.class)
    EventDto createEvent(@Valid EventDto event);
}
