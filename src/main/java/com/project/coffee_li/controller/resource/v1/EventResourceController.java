package com.project.coffee_li.controller.resource.v1;

import com.project.coffee_li.dto.EventDTO;
import com.project.coffee_li.service.EventService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.project.coffee_li.controller.Paths.API_V1_BASE_URL;
import static com.project.coffee_li.controller.Paths.EVENT;
import static com.project.coffee_li.controller.Paths.PATH_EVENT_ID;

@RestController("eventResourceController")
@RequestMapping(
        path = API_V1_BASE_URL,
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
@AllArgsConstructor
@Validated
public class EventResourceController {

    private EventService eventService;

    @GetMapping(EVENT)
    @ResponseStatus(HttpStatus.CREATED)
    public EventDTO createEvent(@Valid @RequestBody EventDTO eventToCreate) {
        return null;
    }

    @PatchMapping(PATH_EVENT_ID)
    @ResponseStatus(HttpStatus.OK)
    public EventDTO updateEvent(@PathVariable UUID eventId, @RequestBody EventDTO updateEvent) {
        return eventService.updateEvent(eventId, updateEvent);
    }

    @DeleteMapping(PATH_EVENT_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean deleteEvent(@PathVariable UUID eventId) {
        return eventService.deleteEvent(eventId);
    }
}
