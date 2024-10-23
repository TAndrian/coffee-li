package com.project.coffee_li.service.impl;

import com.project.coffee_li.dto.EventDTO;
import com.project.coffee_li.mapper.EventMapper;
import com.project.coffee_li.repository.EventRepository;
import com.project.coffee_li.service.EventService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    private EventMapper eventMapper;
    private EventRepository eventRepository;

    @Override
    public List<EventDTO> getEvents() {
        return eventMapper.toDTOs(eventRepository.findAll());
    }
}
