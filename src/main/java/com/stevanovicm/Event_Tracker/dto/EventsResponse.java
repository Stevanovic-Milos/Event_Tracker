package com.stevanovicm.Event_Tracker.dto;

import com.stevanovicm.Event_Tracker.entity.Event;

import java.util.List;

public record EventsResponse(List<Event> events) {
}
