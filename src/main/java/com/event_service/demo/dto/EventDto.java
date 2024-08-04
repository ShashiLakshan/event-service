package com.event_service.demo.dto;

import com.event_service.demo.marker_interfaces.CreateMarker;
import com.event_service.demo.marker_interfaces.UpdateMarker;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName(value = "Event")
public class EventDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(groups = UpdateMarker.class)
    private Integer eventId;

    @NotBlank(groups = CreateMarker.class)
    private String eventName;

    @NotNull(groups = CreateMarker.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @NotBlank(groups = CreateMarker.class)
    private String eventLocation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventDto eventDto = (EventDto) o;
        return Objects.equals(eventId, eventDto.eventId) && Objects.equals(eventName, eventDto.eventName) && Objects.equals(eventDate, eventDto.eventDate) && Objects.equals(eventLocation, eventDto.eventLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, eventName, eventDate, eventLocation);
    }
}
