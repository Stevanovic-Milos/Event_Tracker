package com.stevanovicm.Event_Tracker.service;

import com.stevanovicm.Event_Tracker.dto.*;
import com.stevanovicm.Event_Tracker.entity.User.User;
import com.stevanovicm.Event_Tracker.repository.EventRepository;
import com.stevanovicm.Event_Tracker.entity.Event;
import com.stevanovicm.Event_Tracker.repository.SubscriptionRepository;
import com.stevanovicm.Event_Tracker.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


// Oznaka koja kaže Springu da je ova klasa Spring Bean i da treba da je upravlja (kreira instancu i injektuje zavisnosti)
@Service
//Servis ne treba da zna da koristi JPA - može se promeniti na Hibernate, JDBC itd. bez promene servisa zato koristimo nas Repo
//EventRepository (je već kreirana klasa EventRepository nasleđuje JpaRepository).
@RequiredArgsConstructor
public class EventService {
  private final EventRepository eventRepository;
  private final UserRepository userRepository;
  private final SubscriptionRepository subscriptionRepository;
  private final EmailSenderService emailSenderService;

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
  public Response createEvent(Long userId, UpdateCreateEventRequest updateCreateEventRequest) {
    //trzimo korisnika na osnovu prosledjenog id-a ako ga ne nadjemo bacamo gresku (ne bi bilo lose preriaditi na exsist i vracati response s aporukom d anema korisnika za front)
    User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("korisnik nije pronadjen"));

    //inicijalizujemo nas event tip Event
    Event event = new Event();
    updateEvent(event, updateCreateEventRequest, user);
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

    // pripremamo podatke za mejl trazimo korisnika na osnovu id-a
    User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("korisnik nije pronadjen"));
    // priprema poruke
    String subject="Obrisan event";
    String body = String.format("Event %s je uspešno obrisan ako vi niste obrisali ovaj event predlžemo vam da izmenite lozinku", event.getEventName());

    //slanje emaila
    sendEmail(user.getEmail(),subject,body );

    //vracamo u nasem standardizovanom response formatu
    return new Response("Event uspešno obrisan", true);
  }

  public Response updateEventById(UpdateCreateEventRequest updateCreateEventRequest, User user) {
   Integer eventId = updateCreateEventRequest.eventId();
    if (!eventRepository.existsByIdAndCreatedById(eventId, user.getId())) {
      return new Response("Event ne postoji ili niste njegov kreator", false);
    }
    Event event = eventRepository.findByIdAndCreatedById(eventId, user.getId());

    updateEvent(event, updateCreateEventRequest, user);

    String subject="Izmenjen event";
    String body = String.format("Event %s je uspešno izmenjen ako vi niste izmenili ovaj event predlžemo vam da izmenite lozinku", event.getEventName());

    //slanje emaila
    sendEmail(user.getEmail(),subject,body );

    return new Response("Uspešno izmenjen event", true);
  }



  //kreiranje ili editovanje eventa koristi istu funkciju jer je poenta ista kod kreiranja se pravi nova instanca  new Event dok se kod update-a uzima postojeci
  public void updateEvent(Event event, UpdateCreateEventRequest updateCreateEventRequest, User user) {
    event.setEventName(updateCreateEventRequest.eventName());
    event.setArtist(updateCreateEventRequest.artist());
    event.setAvailableTickets(updateCreateEventRequest.availableTickets());
    event.setCity(updateCreateEventRequest.city());
    event.setCountry(updateCreateEventRequest.country());
    event.setDescription(updateCreateEventRequest.description());
    event.setEndDate(updateCreateEventRequest.endDate());
    event.setEventDate(updateCreateEventRequest.eventDate());
    event.setEventImageUrl(updateCreateEventRequest.eventImageUrl());
    event.setEventVideoUrl(updateCreateEventRequest.eventVideoUrl());
    event.setTicketWebsiteUrl(updateCreateEventRequest.ticketWebsiteUrl());
    event.setGenre(updateCreateEventRequest.genre());
    event.setSoldOut(Optional.ofNullable(updateCreateEventRequest.soldOut()).orElse(false));
    event.setMinAge(updateCreateEventRequest.minAge());
    event.setOrganizer(updateCreateEventRequest.organizer());
    event.setTicketPrice(updateCreateEventRequest.ticketPrice());
    event.setTicketWebsiteUrl(updateCreateEventRequest.ticketWebsiteUrl());
    event.setVenue(updateCreateEventRequest.venue());
    event.setEventVideoUrl(updateCreateEventRequest.eventVideoUrl());
    event.setCreatedBy(user);
    //cuvamo event
    eventRepository.save(event);
  }

  // asik funkcija koja se poziva nezavisno (u drugom thredu)
  @Async
  public void sendEmail(String to, String subject, String body) {
    emailSenderService.sendEmail(to, subject, body);
  }

}
