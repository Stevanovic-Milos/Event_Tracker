package com.stevanovicm.Event_Tracker.controller;

import com.stevanovicm.Event_Tracker.dto.EventsResponse;
import com.stevanovicm.Event_Tracker.dto.Response;
import com.stevanovicm.Event_Tracker.dto.SubscribeRequest;
import com.stevanovicm.Event_Tracker.entity.User.User;
import com.stevanovicm.Event_Tracker.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
  private final SubscriptionService subscriptionService;

  //na adresi api/subscriptions pozivamo funkciju iz servisa kojoj prosledjujemo id korinika  ona vrace listu eventova kreiranih od strane tog korisnika
  @GetMapping
  public ResponseEntity<EventsResponse> getEventsForUser(@AuthenticationPrincipal User user) {
    Long userId = user.getId();
    return ResponseEntity.ok(subscriptionService.getEventsForUser(userId));
  }

  //vadimo id od trenutno ulogovanog korisnika i event koji prosledjujemo ova funkcija pise u bazu sbscriptions taj event i veze ga sa nasim korisnikom
  @PostMapping("/subscribe")
  public ResponseEntity<Response> subscribeToEvent(@AuthenticationPrincipal User user, @RequestBody SubscribeRequest subscribeRequest) {
    Long userId = user.getId();
    return ResponseEntity.ok(subscriptionService.subscribeToEvent(userId, subscribeRequest.eventId()));
  }
}
