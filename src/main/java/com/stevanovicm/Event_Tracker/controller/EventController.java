package com.stevanovicm.Event_Tracker.controller;
import com.stevanovicm.Event_Tracker.entity.Event;
import com.stevanovicm.Event_Tracker.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
  private final EventService eventService;

  @Autowired
  public EventController(EventService eventService) {
    this.eventService = eventService;
  }

  @GetMapping
  public ResponseEntity<List<Event>> getAllEvents() {
    // This will only be reachable if user is authenticated
    return ResponseEntity.ok(eventService.getAllEvents());
  }
}
