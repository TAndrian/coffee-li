package com.project.coffee_li.UT.controller.resource.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.coffee_li.controller.resource.v1.EventResourceController;
import com.project.coffee_li.exception.ConflictException;
import com.project.coffee_li.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.project.coffee_li.controller.Paths.API_V1_BASE_URL;
import static com.project.coffee_li.controller.Paths.EVENT;
import static com.project.coffee_li.controller.Paths.PATH_EVENT_ID;
import static com.project.coffee_li.utils.EventMocks.DISCO_EVENT_ID;
import static com.project.coffee_li.utils.EventMocks.MOCK_CREATED_EVENT_DTO;
import static com.project.coffee_li.utils.EventMocks.MOCK_CREATE_EVENT_DTO;
import static com.project.coffee_li.utils.EventMocks.MOCK_UPDATED_EVENT_DTO;
import static com.project.coffee_li.utils.EventMocks.MOCK_UPDATE_EVENT_DTO;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventResourceController.class)
@AutoConfigureMockMvc
class EventResourceControllerUnitTest {

    private String url = API_V1_BASE_URL;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EventService eventService;

    @Test
    void given_event_to_create_when_createEvent_then_return_created_event() throws Exception {
        // ARRANGE
        url = url + EVENT;
        when(eventService.createEvent(MOCK_CREATE_EVENT_DTO)).thenReturn(MOCK_CREATED_EVENT_DTO);
        String requestBody = objectMapper.writeValueAsString(MOCK_CREATE_EVENT_DTO);
        String expectedResponseBody = objectMapper.writeValueAsString(MOCK_CREATED_EVENT_DTO);

        // ACT
        mockMvc.perform(post(url)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedResponseBody));
    }

    @Test
    void given_event_already_exists_when_createEvent_then_return_conflict_error() throws Exception {
        // ARRANGE
        url = url + EVENT;
        when(eventService.createEvent(MOCK_CREATE_EVENT_DTO)).thenThrow(ConflictException.class);
        String requestBody = objectMapper.writeValueAsString(MOCK_CREATE_EVENT_DTO);

        // ACT
        mockMvc.perform(post(url)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andExpect(status().isConflict());
    }

    @Test
    void given_eventId_and_update_event_when_updateEvent_then_return_updated_event() throws Exception {
        // ARRANGE
        url = url + PATH_EVENT_ID;
        when(eventService.updateEvent(DISCO_EVENT_ID, MOCK_UPDATE_EVENT_DTO))
                .thenReturn(MOCK_UPDATED_EVENT_DTO);

        String requestBody = objectMapper.writeValueAsString(MOCK_UPDATE_EVENT_DTO);
        String expected = objectMapper.writeValueAsString(MOCK_UPDATED_EVENT_DTO);

        // ACT
        mockMvc.perform(patch(url, DISCO_EVENT_ID)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));

    }

    @Test
    void given_eventId_when_deleteEvent_then_return_notContent_response() throws Exception {
        // ARRANGE
        url = url + PATH_EVENT_ID;
        when(eventService.deleteEvent(DISCO_EVENT_ID)).thenReturn(true);

        // ACT
        mockMvc.perform(delete(url, DISCO_EVENT_ID)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }
}
