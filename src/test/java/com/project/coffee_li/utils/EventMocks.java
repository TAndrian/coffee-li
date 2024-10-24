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

    public static final String NEW_EVENT_TITLE = "NEW EVENT";
    public static final String UPDATE_EVENT_TITLE = "UPDATE_EVENT";
    public static final String BOOM_BOOM_NIGHT_TITLE = "BOOM BOOM NIGHT";
    public static final String COFFEE_CHILL_TITLE = "COFFEE & CHILL";
    public static final String WEDDING_TITLE = "WEDDING";

    public static final LocalDate COFFEE_EVENT_DATE = LocalDate.now().plusDays(1);
    public static final LocalDate DISCO_EVENT_DATE = LocalDate.now();
    public static final LocalDate SPECIAL_EVENT_DATE = LocalDate.now().plusDays(2);

    public static final String DISCO_TYPE = "DISCO";
    public static final String COFFEE_NIGHT_TYPE = "COFFEE_NIGHT";
    public static final String SPECIAL_EVENT_TYPE = "SPECIAL_EVENT";

    public static final List<EventDTO> MOCK_EVENTS_DTO = eventDTOs();
    public static final List<EventEntity> MOCK_EVENTS_ENTITY = eventEntities();
    public static final EventDTO MOCK_CREATE_EVENT_DTO = createEventDTO();
    public static final EventEntity MOCK_CREATE_EVENT_ENTITY = createEventEntity();
    public static final EventDTO MOCK_CREATED_EVENT_DTO = createdEventDTO();
    public static final EventDTO MOCK_UPDATE_EVENT_DTO = updateEventDTO();
    public static final EventDTO MOCK_INCORRECT_CREATE_EVENT_DTO = incorrectCreateEventDTO();
    public static final EventEntity MOCK_DISCO_EVENT_ENTITY = MOCK_EVENTS_ENTITY.get(0);
    public static final EventDTO MOCK_UPDATED_EVENT_DTO = updatedEventDTO();
    public static final EventEntity MOCK_UPDATED_EVENT_ENTITY = updatedEventEntity();

    public static List<EventEntity> eventEntities() {
        return List.of(
                EventEntity.builder()
                        .id(EVENT_DISCO_ID)
                        .title(BOOM_BOOM_NIGHT_TITLE)
                        .eventDate(DISCO_EVENT_DATE)
                        .eventType(EventTypeEnum.DISCO)
                        .build(),
                EventEntity.builder()
                        .id(EVENT_COFFEE_ID)
                        .title(COFFEE_CHILL_TITLE)
                        .eventDate(COFFEE_EVENT_DATE)
                        .eventType(EventTypeEnum.COFFEE_NIGHT)
                        .build(),
                EventEntity.builder()
                        .id(SPECIAL_EVENT_ID)
                        .title(WEDDING_TITLE)
                        .eventDate(SPECIAL_EVENT_DATE)
                        .eventType(EventTypeEnum.SPECIAL_EVENT)
                        .build()
        );
    }

    public static List<EventDTO> eventDTOs() {
        List<EventDTO> eventDTOS = new ArrayList<>();
        EventDTO eventDisco = new EventDTO(EVENT_DISCO_ID, BOOM_BOOM_NIGHT_TITLE, DISCO_EVENT_DATE, DISCO_TYPE);
        EventDTO eventCoffee = new EventDTO(EVENT_COFFEE_ID, COFFEE_CHILL_TITLE, COFFEE_EVENT_DATE, COFFEE_NIGHT_TYPE);
        EventDTO specialEvent = new EventDTO(SPECIAL_EVENT_ID, WEDDING_TITLE, SPECIAL_EVENT_DATE, SPECIAL_EVENT_TYPE);
        eventDTOS.add(eventDisco);
        eventDTOS.add(eventCoffee);
        eventDTOS.add(specialEvent);
        return eventDTOS;
    }

    public static EventDTO createEventDTO() {
        return new EventDTO(null, NEW_EVENT_TITLE, LocalDate.now().plusWeeks(1), COFFEE_NIGHT_TYPE);
    }

    public static EventDTO incorrectCreateEventDTO() {
        return new EventDTO(null, null, LocalDate.now().plusWeeks(1), COFFEE_NIGHT_TYPE);
    }

    public static EventEntity createEventEntity() {
        return EventEntity.builder()
                .title(NEW_EVENT_TITLE)
                .eventDate(LocalDate.now().plusWeeks(1))
                .eventType(EventTypeEnum.COFFEE_NIGHT)
                .build();
    }

    public static EventDTO createdEventDTO() {
        return new EventDTO(
                UUID.randomUUID(),
                NEW_EVENT_TITLE,
                MOCK_CREATE_EVENT_DTO.eventDate(),
                COFFEE_NIGHT_TYPE
        );
    }

    public static EventDTO updateEventDTO() {
        return new EventDTO(null, UPDATE_EVENT_TITLE, null, COFFEE_NIGHT_TYPE);
    }

    public static EventEntity updatedEventEntity() {
        return EventEntity.builder()
                .id(EVENT_DISCO_ID)
                .title(UPDATE_EVENT_TITLE)
                .eventDate(MOCK_DISCO_EVENT_ENTITY.getEventDate())
                .eventType(EventTypeEnum.COFFEE_NIGHT)
                .build();
    }

    public static EventDTO updatedEventDTO() {
        return new EventDTO(
                EVENT_DISCO_ID,
                UPDATE_EVENT_TITLE,
                MOCK_DISCO_EVENT_ENTITY.getEventDate(),
                COFFEE_NIGHT_TYPE
        );
    }
}
