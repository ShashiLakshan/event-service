package com.event_service.demo.specification;

import com.event_service.demo.entity.EventEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

public class EventSpecification {

    public static Specification<EventEntity> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                StringUtils.hasText(name) ? criteriaBuilder.like(root.get("name"), "%" + name + "%") : null;
    }

    public static Specification<EventEntity> hasLocation(String location) {
        return (root, query, criteriaBuilder) ->
                StringUtils.hasText(location) ? criteriaBuilder.like(root.get("location"), "%" + location + "%") : null;
    }

    public static Specification<EventEntity> hasDate(LocalDateTime date) {
        return (root, query, criteriaBuilder) ->
                date != null ? criteriaBuilder.equal(root.get("date"), date) : null;
    }
}
