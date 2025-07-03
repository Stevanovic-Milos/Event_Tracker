package com.stevanovicm.Event_Tracker.Controllers;
import com.stevanovicm.Event_Tracker.Objectsdb.Event;
import com.stevanovicm.Event_Tracker.Services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

//Oznacavamo da je ova klasa Spring Rest kontroler
@RestController
//Deklarisemo osnovni url
@RequestMapping("/api/events")
public class EventController {
  private final EventService eventService;

  //Radimo dependency injection, povezujemo se sa servisom koji ima sve operacije koje ovde primenjujemo kroz kontroler
  @Autowired
  public EventController(EventService eventService) {
    this.eventService = eventService;
  }

  //Vraca listu svih eventova
  @GetMapping
  public List<Event> getAllEvents() {
    return eventService.getAllEvents();
  }
//Vraca Event na osnovu id-a
  @GetMapping("/{id}")
  public ResponseEntity<Event> getEventById(@PathVariable Integer id) {
    Optional<Event> event = eventService.getEventById(id);
    return event.map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  //krira novi event
  @PostMapping
  public Event createEvent(@RequestBody Event event) {
    return eventService.createEvent(event);
  }

  //azurira postojeci event
  @PutMapping("/{id}")
  public ResponseEntity<Event> updateEvent(@PathVariable Integer id, @RequestBody Event event) {
    Optional<Event> updatedEvent = eventService.updateEvent(id, event);
    return updatedEvent.map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.notFound().build());
  }
//brise event
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEvent(@PathVariable Integer id) {
    boolean deleted = eventService.deleteEvent(id);
    return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
  }
}
