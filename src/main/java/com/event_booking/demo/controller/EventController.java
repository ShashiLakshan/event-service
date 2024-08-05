package com.event_booking.demo.controller;

import com.event_booking.demo.dto.EventDto;
import com.event_booking.demo.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<EventDto>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<EventDto> getEventById(@PathVariable Integer id) {
        EventDto eventDto = eventService.getEventById(id);
        return ResponseEntity.ok(eventDto);
    }

    @PostMapping(value = "/search", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<EventDto>> searchEvents(@RequestBody EventDto eventDto) {
        List<EventDto> events = eventService.searchEvents(eventDto);
        return ResponseEntity.ok(events);
    }

    @PatchMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<EventDto> updateEvent(@PathVariable Integer id, @RequestBody @Valid EventDto event) {
        EventDto response = eventService.updateEvent(id, event);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Integer id) {
        eventService.deleteEventById(id);
        return ResponseEntity.ok().build();
    }

}
