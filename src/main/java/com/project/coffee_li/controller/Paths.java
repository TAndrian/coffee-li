package com.project.coffee_li.controller;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Paths {
    /* Version path */
    public static final String V1 = "/v1";

    /* Base url paths */
    public static final String VIEW_V1_BASE_URL = "/view" + V1;
    public static final String API_V1_BASE_URL = "/api" + V1;

    /* Slug paths */
    public static final String EVENTS = "/events";
    public static final String EVENT = "/event";
    public static final String EVENT_ID = "/{eventId}";
    public static final String PATH_EVENT_ID = EVENT + EVENT_ID;
}
