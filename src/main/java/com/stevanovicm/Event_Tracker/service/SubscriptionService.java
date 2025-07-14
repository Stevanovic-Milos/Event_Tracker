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

  //ovde vadimo sve eventove iz tabele koji su krirani od strane korisnika ciji id je stigao
  //prsrecemo protok jer nama trebaju smao eventovi mapiramo tabelu da dobijemo smao eventove kolekciju koju dobijemo prtvaramo u lsitu i vracamo tu listu eventova
  public EventsResponse getEventsForUser(Long userId) {
    List<Event> events = subscriptionRepository.findByUserId(userId)
      .stream()
      .map(Subscription::getEvent)
      .collect(Collectors.toList());

    return new EventsResponse(events);
  }

  //ovde se upisuje event u bazu sa korisnikom
  public Response subscribeToEvent(Long userId, Integer eventId) {
    //proveravamo da li su vec povezani u bazi
    if (subscriptionRepository.existsByUserIdAndEventId(userId, eventId)) {
      return new Response("Već pratite ovaj event", false);
    }
    //proveravamo i dodeljujemo korisnika i event koje treba povezati
    User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("korisnik nije pronadjen"));
    Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("event nije pronadjen"));

    //kriramo novu istancu kojoj dodeljujemo dobijena polja
    Subscription subscription = new Subscription();
    subscription.setUser(user);
    subscription.setEvent(event);
    //vreme postavljamo an trenutno vreme kada je unos kreiran
    subscription.setSubscribedAt(LocalDateTime.now());
    //cuvamo u bazi
    subscriptionRepository.save(subscription);

    //vracamo odogov da je sve proslo dobro
    return new Response("Uspešno ste zapratili event", true);
  }
}
