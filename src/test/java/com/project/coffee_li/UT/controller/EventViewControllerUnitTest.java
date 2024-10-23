package com.project.coffee_li.UT.controller;

import com.project.coffee_li.controller.view.EventViewController;
import com.project.coffee_li.service.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.project.coffee_li.controller.Paths.EVENTS;
import static com.project.coffee_li.controller.Paths.VIEW_BASE_URL;
import static com.project.coffee_li.utils.EventMocks.MOCK_EVENTS_DTO;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(EventViewController.class)
@AutoConfigureMockMvc
class EventViewControllerUnitTest {

    @MockBean
    private EventService eventService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_return_events() throws Exception {
        // GIVEN
        String url = VIEW_BASE_URL.concat(EVENTS);
        when(eventService.getEvents()).thenReturn(MOCK_EVENTS_DTO);

        // WHEN
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name("event-view"))
                .andExpect(model().attributeExists("events"));
    }
}
