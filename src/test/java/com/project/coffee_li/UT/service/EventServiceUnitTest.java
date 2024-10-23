package com.project.coffee_li.UT.service;

import com.project.coffee_li.dto.EventDTO;
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

import static com.project.coffee_li.utils.EventMocks.MOCK_EVENTS_DTO;
import static com.project.coffee_li.utils.EventMocks.MOCK_EVENTS_ENTITY;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
