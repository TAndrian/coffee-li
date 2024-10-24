package com.project.coffee_li.UT.service;

import com.project.coffee_li.dto.EventDTO;
import com.project.coffee_li.exception.ConflictException;
import com.project.coffee_li.exception.NotFoundException;
import com.project.coffee_li.mapper.EventMapper;
import com.project.coffee_li.repository.EventRepository;
import com.project.coffee_li.service.impl.EventServiceImpl;
import com.project.coffee_li.utils.EventMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.project.coffee_li.utils.EventMocks.COFFEE_NIGHT_TYPE;
import static com.project.coffee_li.utils.EventMocks.DISCO_EVENT_ID;
import static com.project.coffee_li.utils.EventMocks.MOCK_CREATED_EVENT_DTO;
import static com.project.coffee_li.utils.EventMocks.MOCK_CREATE_EVENT_DTO;
import static com.project.coffee_li.utils.EventMocks.MOCK_CREATE_EVENT_ENTITY;
import static com.project.coffee_li.utils.EventMocks.MOCK_DISCO_EVENT_ENTITY;
import static com.project.coffee_li.utils.EventMocks.MOCK_EVENTS_DTO;
import static com.project.coffee_li.utils.EventMocks.MOCK_EVENTS_ENTITY;
import static com.project.coffee_li.utils.EventMocks.MOCK_INCORRECT_CREATE_EVENT_DTO;
import static com.project.coffee_li.utils.EventMocks.MOCK_UPDATED_EVENT_DTO;
import static com.project.coffee_li.utils.EventMocks.MOCK_UPDATED_EVENT_ENTITY;
import static com.project.coffee_li.utils.EventMocks.MOCK_UPDATE_EVENT_DTO;
import static com.project.coffee_li.utils.EventMocks.NEW_EVENT_TITLE;
import static com.project.coffee_li.utils.EventMocks.UPDATE_EVENT_TITLE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceUnitTest {


    @InjectMocks
    private EventServiceImpl eventService;

    @Mock
    private EventMapper eventMapper;
    @Mock
    private EventRepository eventRepository;

    @Test
    void given_events_when_getEvents_then_return_events() {
        // ARRANGE
        when(eventRepository.findAll())
                .thenReturn(MOCK_EVENTS_ENTITY);
        when(eventMapper.toDTOs(MOCK_EVENTS_ENTITY))
                .thenReturn(MOCK_EVENTS_DTO);

        // ACT
        List<EventDTO> expected = eventService.getEvents();

        // ASSERT
        assertAll(
                () -> verify(eventRepository, times(1)).findAll(),
                () -> verify(eventMapper, times(1)).toDTOs(MOCK_EVENTS_ENTITY),
                () -> assertEquals(EventMocks.eventDTOs(), expected)
        );
    }

    @Test
    void given_correct_createEventDto_when_createEvent_then_return_created_event() {
        // ARRANGE
        when(eventRepository.existsByEventDate(MOCK_CREATE_EVENT_DTO.eventDate()))
                .thenReturn(false);
        when(eventMapper.fromDTO(MOCK_CREATE_EVENT_DTO))
                .thenReturn(MOCK_CREATE_EVENT_ENTITY);
        when(eventRepository.save(MOCK_CREATE_EVENT_ENTITY))
                .thenReturn(any());
        when(eventMapper.toDTO(MOCK_DISCO_EVENT_ENTITY))
                .thenReturn(MOCK_CREATED_EVENT_DTO);

        // ACT
        EventDTO expected = eventService.createEvent(MOCK_CREATE_EVENT_DTO);

        // ASSERT
        assertAll(
                () -> verify(eventRepository, times(1)).existsByEventDate(any()),
                () -> verify(eventRepository, times(1)).save(any()),
                () -> verify(eventMapper, times(1)).fromDTO(MOCK_CREATE_EVENT_DTO),
                () -> verify(eventMapper, times(1)).toDTO(any()),
                () -> assertEquals(NEW_EVENT_TITLE, expected.title()),
                () -> assertEquals(MOCK_CREATE_EVENT_ENTITY.getEventDate(), expected.eventDate()),
                () -> assertEquals(COFFEE_NIGHT_TYPE, expected.eventType())
        );
    }

    @Test
    void given_incorrect_createEventDto_when_createEvent_then_return_bad_request_error() {
        // ARRANGE
        when(eventRepository.existsByEventDate(MOCK_INCORRECT_CREATE_EVENT_DTO.eventDate()))
                .thenReturn(true);

        // ACT
        assertThrows(
                ConflictException.class,
                () -> eventService.createEvent(MOCK_INCORRECT_CREATE_EVENT_DTO)
        );

        // ASSERT
        assertAll(
                () -> verify(eventRepository, times(1)).existsByEventDate(any()),
                () -> verify(eventMapper, times(0)).fromDTO(MOCK_INCORRECT_CREATE_EVENT_DTO),
                () -> verify(eventMapper, times(0)).toDTO(any()),
                () -> verify(eventRepository, times(0)).save(any())
        );
    }

    @Test
    void given_not_found_eventId_when_findEventById_then_throw_not_found_error() {
        // ARRANGE
        UUID mockNotFoundEventId = UUID.randomUUID();

        // ACT
        assertThrows(
                NotFoundException.class,
                () -> eventService.getEventById(mockNotFoundEventId));

        // ASSERT
        assertAll(
                () -> verify(eventMapper, times(0)).toDTO(any())
        );
    }

    @Test
    void given_eventId_and_updateEventDto_when_updateEvent_then_return_updated_event() {
        // ARRANGE
        when(eventRepository.findById(DISCO_EVENT_ID))
                .thenReturn(Optional.ofNullable(MOCK_DISCO_EVENT_ENTITY));
        doNothing().when(eventMapper).updateEventFromDTO(MOCK_UPDATE_EVENT_DTO, MOCK_DISCO_EVENT_ENTITY);

        assert MOCK_DISCO_EVENT_ENTITY != null;
        when(eventRepository.save(MOCK_DISCO_EVENT_ENTITY)).thenReturn(MOCK_UPDATED_EVENT_ENTITY);
        when(eventMapper.toDTO(MOCK_UPDATED_EVENT_ENTITY)).thenReturn(MOCK_UPDATED_EVENT_DTO);

        // ACT
        EventDTO expected = eventService.updateEvent(DISCO_EVENT_ID, MOCK_UPDATE_EVENT_DTO);

        // ASSERT
        assertAll(
                () -> verify(eventRepository, times(1)).findById(DISCO_EVENT_ID),
                () -> verify(eventRepository, times(1)).save(any()),
                () -> verify(eventMapper, times(1))
                        .updateEventFromDTO(MOCK_UPDATE_EVENT_DTO, MOCK_DISCO_EVENT_ENTITY),
                () -> verify(eventMapper, times(1)).toDTO(any()),
                () -> assertEquals(UPDATE_EVENT_TITLE, expected.title()),
                () -> assertEquals(COFFEE_NIGHT_TYPE, expected.eventType())
        );
    }

    @Test
    void given_eventId_when_deleteEvent_then_delete_event_and_return_true() {
        // ARRANGE
        when(eventRepository.findById(DISCO_EVENT_ID))
                .thenReturn(Optional.ofNullable(MOCK_DISCO_EVENT_ENTITY));

        assert MOCK_DISCO_EVENT_ENTITY != null;
        doNothing().when(eventRepository).delete(MOCK_DISCO_EVENT_ENTITY);

        // ACT
        boolean expected = eventService.deleteEvent(DISCO_EVENT_ID);

        // ASSERT
        assertAll(
                () -> assertTrue(expected),
                () -> verify(eventRepository, times(1)).delete(MOCK_DISCO_EVENT_ENTITY)
        );
    }
}
