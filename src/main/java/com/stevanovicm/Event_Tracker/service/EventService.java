package com.stevanovicm.Event_Tracker.service;

import com.stevanovicm.Event_Tracker.dto.CreateEventRequest;
import com.stevanovicm.Event_Tracker.dto.EventsResponse;
import com.stevanovicm.Event_Tracker.dto.Response;
import com.stevanovicm.Event_Tracker.dto.SingleEventResponse;
import com.stevanovicm.Event_Tracker.entity.User.User;
import com.stevanovicm.Event_Tracker.repository.EventRepository;
import com.stevanovicm.Event_Tracker.entity.Event;
import com.stevanovicm.Event_Tracker.repository.SubscriptionRepository;
import com.stevanovicm.Event_Tracker.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


// Oznaka koja kaže Springu da je ova klasa Spring Bean i da treba da je upravlja (kreira instancu i injektuje zavisnosti)
@Service
//Servis ne treba da zna da koristi JPA - može se promeniti na Hibernate, JDBC itd. bez promene servisa zato koristimo nas Repo
//EventRepository (je već kreirana klasa EventRepository nasleđuje JpaRepository).
@RequiredArgsConstructor
public class EventService {
  private final EventRepository eventRepository;
  private final UserRepository userRepository;
  private final SubscriptionRepository subscriptionRepository;

  //Vraca sve Eventove
  public EventsResponse getAllEvents() {
    List<Event> events = eventRepository.findAll();
    return new EventsResponse(events);
  }

  //Nalazi Event na osnovu Id-a
  public SingleEventResponse getEventById(Integer eventId) {
    if (eventRepository.existsById(eventId)) {
      Event event = eventRepository.findById(eventId).orElseThrow();
      return new SingleEventResponse(event, true);
    }
    return new SingleEventResponse(null, false);
  }

  //ovo je funkcija tipa Response sto je nas standardan format responsa sa porukom i boolom sucess koji oznacava da li je nesto uspesno proslo ili ne
  public Response createEvent(Long userId, CreateEventRequest createEventRequest) {
    //trzimo korisnika na osnovu prosledjenog id-a ako ga ne nadjemo bacamo gresku (ne bi bilo lose preriaditi na exsist i vracati response s aporukom d anema korisnika za front)
    User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("korisnik nije pronadjen"));

    //inicijalizujemo nas event tip Event kome dodljujemo sve sto dobijamo od CreateEventRequesta koji je nas javni record sa tipovima i podacima koje mozemo ocekivati
    Event event = new Event();
    //svakom polju naseg eventa se prkeo seta dodelju vrednost dobijena sa fronta
    event.setEventName(createEventRequest.eventName());
    event.setArtist(createEventRequest.artist());
    event.setAvailableTickets(createEventRequest.availableTickets());
    event.setCity(createEventRequest.city());
    event.setCountry(createEventRequest.country());
    event.setDescription(createEventRequest.description());
    event.setEndDate(createEventRequest.endDate());
    event.setEventDate(createEventRequest.eventDate());
    event.setEventImageUrl(createEventRequest.eventImageUrl());
    event.setEventVideoUrl(createEventRequest.eventVideoUrl());
    event.setTicketWebsiteUrl(createEventRequest.ticketWebsiteUrl());
    event.setGenre(createEventRequest.genre());
    event.setSoldOut(false);
    event.setMinAge(createEventRequest.minAge());
    event.setOrganizer(createEventRequest.organizer());
    event.setTicketPrice(createEventRequest.ticketPrice());
    event.setTicketWebsiteUrl(createEventRequest.ticketWebsiteUrl());
    event.setVenue(createEventRequest.venue());
    event.setEventVideoUrl(createEventRequest.eventVideoUrl());
    //ovo bi bilo bolje sve preraditi da bude smao id korisnika kako bi izbegli kada vracamo event da se dobijaju svi podaci o korisniku
    event.setCreatedBy(user);
    //cuvamo event
    eventRepository.save(event);
    //vracamo poruku i sucess
    return new Response("Uspešno kreiran event", true);
  }

  //EventsResponse je ans record dto koji vraca listu eventova
  public EventsResponse getEventsCreatedByUser(Long userId) {
    //lista svih eventova koji su krirani od korisnika sa prosledjenim id-em
    List<Event> events = eventRepository.findByCreatedBy_Id(userId);
    return new EventsResponse(events);
  }

  @Transactional
  public Response deleteEvent(Integer eventId, Long userId) {
    //radimo proveru da ustanovimo da li dozvoliti korisniku brisanje
    if (!eventRepository.existsByIdAndCreatedById(eventId, userId)) {
      return new Response("Event ne postoji ili niste njegov kreator", false);
    }
    //trazimo event sa nastim costume kverijem gde vracamo event kome je kreator nas ulogovani korisnik i event id zahtevani event
    Event event = eventRepository.findByIdAndCreatedById(eventId, userId);
    //prvo brisemo iz subscription table da ne bi dobili sql gresku veze sa eventom koji ne postoji
    subscriptionRepository.deleteByEventId(eventId);
    //brisemo event
    eventRepository.delete(event);

    //vracamo u nasem standardizovanom response formatu
    return new Response("Event uspešno obrisan", true);
  }

}
