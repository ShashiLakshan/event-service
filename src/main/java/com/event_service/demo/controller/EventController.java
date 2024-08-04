package com.event_service.demo.controller;

import com.event_service.demo.dto.EventDto;
import com.event_service.demo.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventService eventService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<EventDto> createEvent(@RequestBody @Valid EventDto event) {
        EventDto response = eventService.createEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
