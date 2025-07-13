package com.stevanovicm.Event_Tracker.controller;
import com.stevanovicm.Event_Tracker.dto.EventRequest;
import com.stevanovicm.Event_Tracker.dto.EventsResponse;
import com.stevanovicm.Event_Tracker.dto.SingleEventResponse;
import com.stevanovicm.Event_Tracker.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class EventController {
  private final EventService eventService;

  @Autowired
  public EventController(EventService eventService) {
    this.eventService = eventService;
  }

  @GetMapping
  public ResponseEntity<EventsResponse> getAllEvents() {
    return ResponseEntity.ok(eventService.getAllEvents());
  }
  @PostMapping("/details")
  public ResponseEntity<SingleEventResponse> getEventById(@RequestBody EventRequest eventRequest) {
    return ResponseEntity.ok(eventService.getEventById(eventRequest.eventId()));
  }
}
