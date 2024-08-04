package com.event_service.demo.service.impl;

import com.event_service.demo.dto.EventDto;
import com.event_service.demo.entity.EventEntity;
import com.event_service.demo.mapper.EventMapper;
import com.event_service.demo.repository.EventRepository;
import com.event_service.demo.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Transactional
    @Override
    public EventDto createEvent(EventDto event) {
        EventEntity eventEntity = eventRepository.save(EventMapper.toEntity(event));
        return EventMapper.toDto(eventEntity);
    }
}
