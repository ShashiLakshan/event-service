package com.event_service.demo.mapper;

import com.event_service.demo.dto.EventDto;
import com.event_service.demo.dto.TicketDto;
import com.event_service.demo.entity.EventEntity;
import com.event_service.demo.entity.TicketEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.stream.Collectors;

public class EventMapper {

    private EventMapper(){}

    public static EventDto toDto(EventEntity entity) {
        return EventDto.builder()
                .eventId(entity.getId())
                .eventDate(entity.getDate())
                .eventLocation(entity.getLocation())
                .eventName(entity.getName())
                .tickets(entity.getTickets().stream().map(EventMapper::toTicketDto).collect(Collectors.toSet()))
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

        EventEntity entity = builder.build();
        Set<TicketEntity> ticketEntitySet = dto.getTickets().stream().map(EventMapper::toTicketEntity).collect(Collectors.toSet());
        ticketEntitySet.forEach(ticket -> ticket.setEvent(entity));
        entity.setTicketSet(ticketEntitySet);
        return entity;
    }

    public static EventEntity toUpdateEntity(EventDto dto, EventEntity entity) {

        if (StringUtils.hasText(dto.getEventName()) && !dto.getEventName().equals(entity.getName())) {
            entity.setName(dto.getEventName());
        }
        if (StringUtils.hasText(dto.getEventLocation()) && !dto.getEventLocation().equals(entity.getLocation())) {
            entity.setLocation(dto.getEventLocation());
        }
        if (!ObjectUtils.isEmpty(dto.getEventDate()) && !dto.getEventDate().equals(entity.getDate())) {
            entity.setDate(dto.getEventDate());
        }

        return entity;
    }

    public static TicketEntity toTicketEntity(TicketDto dto) {
        TicketEntity.TicketEntityBuilder builder = TicketEntity.builder()
                .noOfTickets(dto.getNoOfTickets())
                .ticketType(dto.getTicketType())
                .unitPrice(dto.getUnitPrice());

        if (!ObjectUtils.isEmpty(dto.getTicketId())) {
            builder.id(dto.getTicketId());
        }

        return builder.build();
    }

    public static TicketDto toTicketDto(TicketEntity entity) {
        return TicketDto.builder()
                .ticketId(entity.getId())
                .noOfTickets(entity.getNoOfTickets())
                .ticketType(entity.getTicketType())
                .unitPrice(entity.getUnitPrice())
                .build();
    }
}
