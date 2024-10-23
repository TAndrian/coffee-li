package com.project.coffee_li.controller.view;

import com.project.coffee_li.dto.EventDTO;
import com.project.coffee_li.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static com.project.coffee_li.controller.Paths.EVENTS;
import static com.project.coffee_li.controller.Paths.VIEW_BASE_URL;

@Controller
@RequestMapping(path = VIEW_BASE_URL)
@AllArgsConstructor
public class EventViewController {

    private EventService eventService;

    @GetMapping(EVENTS)
    @ResponseStatus(HttpStatus.OK)
    public String eventsView(Model model) {
        List<EventDTO> events = eventService.getEvents();
        model.addAttribute("events", events);
        return "event-view";
    }
}
