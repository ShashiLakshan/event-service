package com.event_booking.demo.controller;

import com.event_booking.demo.dto.EventDto;
import com.event_booking.demo.dto.TicketDto;
import com.event_booking.demo.enums.TicketType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
public class EventControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private EventDto eventDto;
    private Set<TicketDto> ticketDtos;

    String dateTimeString;
    DateTimeFormatter formatter;
    LocalDateTime eventDate;

    @BeforeEach
    public void setUp() {
        dateTimeString = "2022-12-31 23:59:00";
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        eventDate = LocalDateTime.parse(dateTimeString, formatter);
        ticketDtos = Set.of(
                TicketDto.builder()
                        .ticketType(TicketType.VIP)
                        .noOfTickets(10)
                        .unitPrice(BigDecimal.valueOf(1000))
                        .build(),

                TicketDto.builder()
                        .ticketType(TicketType.REGULAR)
                        .noOfTickets(20)
                        .unitPrice(BigDecimal.valueOf(100))
                        .build()
        );
        eventDto = EventDto.builder()
                .eventName("Test_Event")
                .eventDate(eventDate)
                .eventLocation("Colombo")
                .tickets(ticketDtos)
                .build();
    }

    @Order(1)
    @Test
    public void whenValidEventDtoGiven_thenEventShouldBeCreated() throws Exception {
        mockMvc.perform(post("/api/v1/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.eventName").value("Test_Event"))
                .andExpect(jsonPath("$.eventDate").value(dateTimeString))
                .andExpect(jsonPath("$.eventLocation").value("Colombo"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Order(2)
    @Test
    public void whenEventNameIsEmpty_thenExceptionShouldBeOccurred() throws Exception {
        eventDto.setEventName("");
        mockMvc.perform(post("/api/v1/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].code").value("APP_400"))
                .andExpect(jsonPath("$[0].message").value("EventName must not be blank"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Order(3)
    @Test
    public void whenGettingEventById_thenEventShouldBeReturned() throws Exception {

        String responseContent = mockMvc.perform(post("/api/v1/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        EventDto createEventDto = objectMapper.readValue(responseContent, EventDto.class);

        mockMvc.perform(get("/api/v1/events/{id}", createEventDto.getEventId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventId").value(createEventDto.getEventId()))
                .andExpect(jsonPath("$.eventName").value("Test_Event"))
                .andExpect(jsonPath("$.eventDate").value(dateTimeString))
                .andExpect(jsonPath("$.eventLocation").value("Colombo"))
                .andDo(MockMvcResultHandlers.print());
    }

}
