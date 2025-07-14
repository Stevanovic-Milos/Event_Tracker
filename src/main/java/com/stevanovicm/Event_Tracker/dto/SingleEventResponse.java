package com.stevanovicm.Event_Tracker.dto;

import com.stevanovicm.Event_Tracker.entity.Event;

public record SingleEventResponse(Event event, boolean success) {
}
