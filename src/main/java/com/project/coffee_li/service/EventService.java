package com.project.coffee_li.service;

import com.project.coffee_li.dto.EventDTO;

import java.util.List;

public interface EventService {
    /**
     * Retrieve all events.
     * @return events.
     */
    List<EventDTO> getEvents();
}
