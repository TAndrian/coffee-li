package com.project.coffee_li.exception.enums.v1;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventExceptionEnum {
    EVENT_EXCEPTION_CODE("v1.resource.event"),
    EVENT_ALREADY_EXISTS("Event already exists."),
    EVENT_NOT_FOUND("Event not found.");
    private final String value;
}
