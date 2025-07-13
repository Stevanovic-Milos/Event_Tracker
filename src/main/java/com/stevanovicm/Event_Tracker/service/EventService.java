package com.stevanovicm.Event_Tracker.service;
import com.stevanovicm.Event_Tracker.dto.EventsResponse;
import com.stevanovicm.Event_Tracker.dto.SingleEventResponse;
import com.stevanovicm.Event_Tracker.repository.EventRepository;
import com.stevanovicm.Event_Tracker.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

// Oznaka koja kaže Springu da je ova klasa Spring Bean i da treba da je upravlja (kreira instancu i injektuje zavisnosti)
@Service
//Servis ne treba da zna da koristi JPA - može se promeniti na Hibernate, JDBC itd. bez promene servisa zato koristimo nas Repo
//EventRepository (je već kreirana klasa EventRepository nasleđuje JpaRepository).
public class EventService {
  private final EventRepository eventRepository;

//Spring automatski ubacuje (injektuje) implementaciju povezuje se na repo koji je implementacija sa bazom
  @Autowired
  public EventService(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  //Vraca sve Eventove
  public EventsResponse getAllEvents() {
    List<Event> events = eventRepository.findAll();
    return new EventsResponse(events);
  }

  //Nalazi Event na osnovu Id-a
  public SingleEventResponse getEventById(Integer eventId) {
    if(eventRepository.existsById(eventId)){
      Event event = eventRepository.findById(eventId).orElseThrow();
      return new SingleEventResponse(event, true);
    }
    return new SingleEventResponse(null, false);
  }

}
