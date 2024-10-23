package com.project.coffee_li.utils;

import com.project.coffee_li.dto.EventDTO;
import com.project.coffee_li.model.EventEntity;
import com.project.coffee_li.model.EventTypeEnum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EventMocks {
    public static final UUID EVENT_DISCO_ID = UUID.randomUUID();
    public static final UUID EVENT_COFFEE_ID = UUID.randomUUID();
    public static final UUID SPECIAL_EVENT_ID = UUID.randomUUID();
    public static final String DISCO_TYPE = "DISCO";
    public static final String COFFEE_TYPE = "COFFEE_NIGHT";
    public static final String SPECIAL_EVENT_TYPE = "SPECIAL_EVENT";
    public static final LocalDate COFFEE_EVENT_DATE = LocalDate.now().plusDays(1);
    public static final LocalDate DISCO_EVENT_DATE = LocalDate.now();
    public static final LocalDate SPECIAL_EVENT_DATE = LocalDate.now().plusDays(2);
    public static final List<EventDTO> MOCK_EVENTS_DTO = eventDTOs();
    public static final List<EventEntity> MOCK_EVENTS_ENTITY = eventEntities();

    public static List<EventEntity> eventEntities() {
        return List.of(
                EventEntity.builder()
                        .id(EVENT_DISCO_ID)
                        .eventDate(DISCO_EVENT_DATE)
                        .eventType(EventTypeEnum.DISCO)
                        .build(),
                EventEntity.builder()
                        .id(EVENT_COFFEE_ID)
                        .eventDate(COFFEE_EVENT_DATE)
                        .eventType(EventTypeEnum.COFFEE_NIGHT)
                        .build(),
                EventEntity.builder()
                        .id(EVENT_DISCO_ID)
                        .eventDate(SPECIAL_EVENT_DATE)
                        .eventType(EventTypeEnum.SPECIAL_EVENT)
                        .build()
        );
    }

    public static List<EventDTO> eventDTOs() {
        List<EventDTO> eventDTOS = new ArrayList<>();
        EventDTO eventDisco = new EventDTO(EVENT_DISCO_ID, DISCO_EVENT_DATE, DISCO_TYPE);
        EventDTO eventCoffee = new EventDTO(EVENT_COFFEE_ID, COFFEE_EVENT_DATE, COFFEE_TYPE);
        EventDTO specialEvent = new EventDTO(SPECIAL_EVENT_ID, SPECIAL_EVENT_DATE, SPECIAL_EVENT_TYPE);
        eventDTOS.add(eventDisco);
        eventDTOS.add(eventCoffee);
        eventDTOS.add(specialEvent);
        return eventDTOS;
    }
}
