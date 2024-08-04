package com.event_service.demo.dto;

import com.event_service.demo.enums.TicketType;
import com.event_service.demo.marker_interfaces.CreateMarker;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketDto {
    private Integer ticketId;
    @NotNull(groups = CreateMarker.class)
    private TicketType ticketType;
    @NotNull(groups = CreateMarker.class)
    private Integer noOfTickets;
    @NotNull(groups = CreateMarker.class)
    private BigDecimal unitPrice;
}
