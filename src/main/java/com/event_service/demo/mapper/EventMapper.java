package com.event_service.demo.mapper;

import com.event_service.demo.dto.EventDto;
import com.event_service.demo.entity.EventEntity;
import org.springframework.util.ObjectUtils;

public class EventMapper {

    private EventMapper(){}

    public static EventDto toDto(EventEntity entity) {
        return EventDto.builder()
                .eventId(entity.getId())
                .eventDate(entity.getDate())
                .eventLocation(entity.getLocation())
                .eventName(entity.getName())
                .build();
    }

    public static EventEntity toEntity(EventDto dto) {
        EventEntity.EventEntityBuilder builder = EventEntity.builder()
                .date(dto.getEventDate())
                .name(dto.getEventName())
                .location(dto.getEventLocation());

        if (!ObjectUtils.isEmpty(dto.getEventId())) {
            builder.id(dto.getEventId());
        }

        return builder.build();
    }
}