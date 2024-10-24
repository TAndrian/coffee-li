package com.project.coffee_li.service.impl;

import com.project.coffee_li.dto.EventDTO;
import com.project.coffee_li.exception.NotFoundException;
import com.project.coffee_li.exception.enums.v1.EventExceptionEnum;
import com.project.coffee_li.mapper.EventMapper;
import com.project.coffee_li.model.EventEntity;
import com.project.coffee_li.repository.EventRepository;
import com.project.coffee_li.service.EventService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
@AllArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    public static final String EVENT_NOT_FOUND_WITH_THE_GIVEN_ID_LOG = "Event not found with the given id: {}";
    private EventMapper eventMapper;
    private EventRepository eventRepository;

    @Override
    public List<EventDTO> getEvents() {
        return eventMapper.toDTOs(eventRepository.findAll());
    }

    @Override
    public EventDTO getEventById(UUID evenId) {
        return eventMapper.toDTO(findEventById(evenId));
    }

    @Override
    public EventDTO createEvent(EventDTO eventToCreate) {
        return null;
    }

    @Override
    public EventDTO updateEvent(UUID eventId, EventDTO updateEvent) {
        return null;
    }

    @Override
    public boolean deleteEvent(UUID eventId) {
        return false;
    }

    /**
     * Find event by event id reference.
     *
     * @param eventId reference to event.
     * @return event.
     */
    private EventEntity findEventById(UUID eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> {
                    log.info(EVENT_NOT_FOUND_WITH_THE_GIVEN_ID_LOG, eventId);
                    return new NotFoundException(
                            EventExceptionEnum.EVENT_NOT_FOUND.getValue(),
                            EventExceptionEnum.EVENT_EXCEPTION_CODE.getValue()
                    );
                });
    }
}
