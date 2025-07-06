package com.stevanovicm.Event_Tracker.service;
import com.stevanovicm.Event_Tracker.repository.EventRepository;
import com.stevanovicm.Event_Tracker.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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
  public List<Event> getAllEvents() {
    return eventRepository.findAll();
  }

  //Nalazi Event na osnovu Id-a
  public Optional<Event> getEventById(Integer id) {
    return eventRepository.findById(id);
  }

  //Kreira novi event
  public Event createEvent(Event event) {
    return eventRepository.save(event);
  }

  //Azurira postojeci Event na osnovu Id-a
  public Optional<Event> updateEvent(Integer id, Event updatedEvent) {
    Optional<Event> existingEvent = eventRepository.findById(id);
    if (existingEvent.isPresent()) {
      Event event = existingEvent.get();
      event.setEventName(updatedEvent.getEventName());
      return Optional.of(eventRepository.save(event));
    }
    return Optional.empty();
  }

  //Brise event
  public boolean deleteEvent(Integer id) {
    if (eventRepository.existsById(id)) {
      eventRepository.deleteById(id);
      return true;
    }
    return false;
  }
}
