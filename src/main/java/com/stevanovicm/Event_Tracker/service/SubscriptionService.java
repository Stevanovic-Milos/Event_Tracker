package com.stevanovicm.Event_Tracker.service;

import com.stevanovicm.Event_Tracker.dto.EventsResponse;
import com.stevanovicm.Event_Tracker.dto.Response;
import com.stevanovicm.Event_Tracker.entity.Event;
import com.stevanovicm.Event_Tracker.entity.Subscription;
import com.stevanovicm.Event_Tracker.entity.User.User;
import com.stevanovicm.Event_Tracker.repository.EventRepository;
import com.stevanovicm.Event_Tracker.repository.SubscriptionRepository;
import com.stevanovicm.Event_Tracker.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
  private final SubscriptionRepository subscriptionRepository;
  private final EventRepository eventRepository;
  private final UserRepository userRepository;

  public EventsResponse getEventsForUser(Long userId) {
    List<Event> events = subscriptionRepository.findByUserId(userId)
      .stream()
      .map(Subscription::getEvent)
      .collect(Collectors.toList());

    return new EventsResponse(events);
  }

  public Response subscribeToEvent(Long userId, Integer eventId) {
    if(subscriptionRepository.existsByUserIdAndEventId(userId, eventId)){
      return new Response("korisnik je vec prijavljen na event", false);
    }
    User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("korisnik nije pronadjen"));
    Event event = eventRepository.findById(eventId).orElseThrow(()-> new EntityNotFoundException("event nije pronadjen"));

    Subscription subscription = new Subscription();
    subscription.setUser(user);
    subscription.setEvent(event);
    subscription.setSubscribedAt(LocalDateTime.now());
    subscriptionRepository.save(subscription);

    return new Response("korisnik uspesno prijavljen na event", true);
  }
}
