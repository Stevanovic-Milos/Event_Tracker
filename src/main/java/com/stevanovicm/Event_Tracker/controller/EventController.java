package com.stevanovicm.Event_Tracker.controller;

import com.stevanovicm.Event_Tracker.dto.*;
import com.stevanovicm.Event_Tracker.entity.User.User;
import com.stevanovicm.Event_Tracker.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EventController {
  //ubacujemo event servis
  private final EventService eventService;

  //na ruti /api/events kojoj svako moze pristupiti vracamo  nase eventove tipa event pozivom servisa
  @GetMapping("/events")
  public ResponseEntity<EventsResponse> getAllEvents() {
    return ResponseEntity.ok(eventService.getAllEvents());
  }

  //n ruti api/events/details sa fronta post metodom prosledjujemo id eventa ovde g auzimamo iz request bodija  i prosledjujemo nasoj getEventbyid funkciji, svako moze da joj pristupu
  @PostMapping("events/details")
  public ResponseEntity<SingleEventResponse> getEventById(@RequestBody EventRequest eventRequest) {
    return ResponseEntity.ok(eventService.getEventById(eventRequest.eventId()));
  }

  //admin only sekcija u security configu je podeseno da svaki poziv koji kreve sa /api/admin moze pristupiti smao amdin
  // ovde se kreira event dodljuje se user cuopa id i iz tela se uzimaju prkeo naseg dto recorda createeventrequest se slaju svi podaci potrebni servisnoj funkciji
  @PostMapping("admin/events/create")
  public ResponseEntity<Response> createEvent(@AuthenticationPrincipal User user, @RequestBody CreateEventRequest createEventRequest) {
    Long userId = user.getId();
    return ResponseEntity.ok(eventService.createEvent(userId, createEventRequest));
  }

  //ovo je takodje admin poziv kojem smao admin moze pristupiti cupa id iz usera i prosledjumo dlaje servis nam vrati sve eventove koje je krirao zadati korisnik
  @GetMapping("/admin/events/user")
  public ResponseEntity<EventsResponse> getEventsForUser(@AuthenticationPrincipal User user) {
    Long userId = user.getId();
    return ResponseEntity.ok(eventService.getEventsCreatedByUser(userId));
  }

  //klasicn rest nacin poziva delete metode koja briose nas event iz urla cupamo eventid koji prosledjujemo servisu zajdeno sa idem korisnika servis proveri
  //da li je ans korisnik kretor i ako jete obrise event vraca klasican respons sa porukom i uspehom
  @DeleteMapping("/admin/events/delete/{eventId}")
  public ResponseEntity<Response> deleteEvent(@PathVariable Integer eventId, @AuthenticationPrincipal User user) {

    return ResponseEntity.ok(eventService.deleteEvent(eventId, user.getId()));
  }

}
