package com.project.coffee_li.dto;

import java.time.LocalDate;
import java.util.UUID;

public record EventDTO(
        UUID id,
        LocalDate eventDate,
        String eventType
) {
}
