package com.project.coffee_li.service;

import com.project.coffee_li.dto.EventDTO;

import java.util.List;
import java.util.UUID;

public interface EventService {
    /**
     * Retrieve all events.
     *
     * @return events.
     */
    List<EventDTO> getEvents();

    /**
     * Retrieve event by event id reference.
     *
     * @param evenId reference id to event.
     * @return Event.
     */
    EventDTO getEventById(UUID evenId);

    /**
     * Create event from eventToCreate data.
     *
     * @param eventToCreate event to create data.
     * @return created event.
     */
    EventDTO createEvent(EventDTO eventToCreate);

    /**
     * Update existing event from updateEvent data.
     *
     * @param eventId     reference id to event.
     * @param updateEvent update data.
     * @return updated event.
     */
    EventDTO updateEvent(UUID eventId, EventDTO updateEvent);

    /**
     * Delete existing event.
     *
     * @param eventId reference id to event.
     * @return True if event deleted successfully.
     */
    boolean deleteEvent(UUID eventId);
}
