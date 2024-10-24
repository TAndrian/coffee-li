package com.project.coffee_li.IT.resource.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.coffee_li.dto.EventDTO;
import com.project.coffee_li.mapper.EventMapper;
import com.project.coffee_li.model.EventEntity;
import com.project.coffee_li.repository.EventRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.project.coffee_li.controller.Paths.API_V1_BASE_URL;
import static com.project.coffee_li.controller.Paths.EVENT;
import static com.project.coffee_li.controller.Paths.PATH_EVENT_ID;
import static com.project.coffee_li.utils.EventMocks.MOCK_CREATE_EVENT_ENTITY;
import static com.project.coffee_li.utils.EventMocks.MOCK_DISCO_EVENT_ENTITY;
import static com.project.coffee_li.utils.EventMocks.MOCK_INCORRECT_CREATE_EVENT_DTO;
import static com.project.coffee_li.utils.EventMocks.MOCK_UPDATE_EVENT_DTO;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = "/data.sql")
@Testcontainers
class EventResourceControllerIntegrationTest {

    public static final UUID TARGET_EVENT_ID = UUID.fromString("199f7437-f67b-4d4c-8c2e-8578a250affd");
    private static String url = "http://localhost:";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventMapper eventMapper;

    @LocalServerPort
    private static Integer port;

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("coffee_li_db_test")
            .withUsername("postgres")
            .withPassword("123456");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @BeforeAll
    static void setUp() {
        url = url + port + API_V1_BASE_URL;
        postgreSQLContainer.start();
    }

    @AfterAll
    static void tearDown() {
        postgreSQLContainer.stop();
    }

    @AfterEach
    void cleanUp() {
        eventRepository.deleteAll();
    }


    @Test
    void given_event_to_create_should_create_event() throws Exception {
        // ARRANGE
        final String URL = url + EVENT;
        String requestBody = objectMapper.writeValueAsString(MOCK_CREATE_EVENT_ENTITY);

        // ACT
        MvcResult mvcResult = mockMvc.perform(post(URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn();

        // ASSERT
        EventDTO expected = convertResponseToEventDTO(mvcResult);

        // If event was successfully created, then existsByEventDate should return true.
        assertTrue(eventRepository.existsByEventDate(expected.eventDate()));
    }

    @Test
    void given_incorrect_event_to_create_should_return_bad_request_error() throws Exception {
        // ARRANGE
        final String URL = url + EVENT;
        String requestBody = objectMapper.writeValueAsString(MOCK_INCORRECT_CREATE_EVENT_DTO);
        Set<String> expectedErrors = Set.of("Event title must be provided.");

        // ACT
        MvcResult mvcResult = mockMvc.perform(post(URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andReturn();

        // ASSERT
        /* Extract message values from mvcResult */
        String message = extractErrorResponse(mvcResult);
        Set<String> actualErrors = new HashSet<>(Arrays.asList(message.split("; ")));

        assertEquals(expectedErrors, actualErrors);
    }

    @Test
    void given_event_that_already_exists_should_return_conflict_error() throws Exception {
        // ARRANGE
        final String URL = url + EVENT;
        final EventEntity existingEventEntity = MOCK_DISCO_EVENT_ENTITY;
        existingEventEntity.setEventDate(LocalDate.parse("2024-12-01"));
        eventRepository.findAll();
        String requestBody = objectMapper.writeValueAsString(existingEventEntity);

        // ACT
        mockMvc.perform(post(URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isConflict());
    }

    @Test
    void given_eventId_and_update_event_should_return_updated_event() throws Exception {
        // ARRANGE
        final String URL = url + PATH_EVENT_ID;

        String requestBody = objectMapper.writeValueAsString(MOCK_UPDATE_EVENT_DTO);

        // ACT
        MvcResult mvcResult = mockMvc.perform(patch(URL, TARGET_EVENT_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();

        // ASSERT
        EventDTO expected = convertResponseToEventDTO(mvcResult);
        EventEntity eventEntity = eventRepository.findById(TARGET_EVENT_ID)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));
        EventDTO eventDTO = eventMapper.toDTO(eventEntity);
        assertAll(
                () -> assertEquals(eventDTO, expected),
                () -> assertEquals(MOCK_UPDATE_EVENT_DTO.title(), expected.title()),
                () -> assertEquals(MOCK_UPDATE_EVENT_DTO.eventType(), expected.eventType())
        );
    }

    @Test
    void given_eventId_should_delete_event() throws Exception {
        // ARRANGE
        final String URL = url + PATH_EVENT_ID;

        // ACT
        mockMvc.perform(delete(URL, TARGET_EVENT_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    /**
     * Convert response body into EventDTO format.
     *
     * @param mvcResult api response.
     * @return Event DTO
     * @throws UnsupportedEncodingException exception.
     * @throws JsonProcessingException      exception.
     */
    private EventDTO convertResponseToEventDTO(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        String responseBody = mvcResult.getResponse().getContentAsString();
        return objectMapper.readValue(responseBody, EventDTO.class);
    }

    /**
     * Extract error response values from message field annotated in DTOs in mvcResult.
     *
     * @param mvcResult response.
     * @return Extract value from message field.
     * @throws UnsupportedEncodingException exception.
     * @throws JsonProcessingException      exception.
     */
    private String extractErrorResponse(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        String responseBody = mvcResult.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("message").asText();
    }
}
