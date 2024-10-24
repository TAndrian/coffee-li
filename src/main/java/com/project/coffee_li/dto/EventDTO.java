package com.project.coffee_li.dto;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.UUID;

public record EventDTO(
        UUID id,
        @NotEmpty(message = "Event title must be provided.")
        String title,
        @NotEmpty(message = "Event date must be provided.")
        LocalDate eventDate,
        @NotEmpty(message = "Event type must be provided.")
        String eventType
) {
}
