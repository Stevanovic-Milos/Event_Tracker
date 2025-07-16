package com.stevanovicm.Event_Tracker.repository;

import com.stevanovicm.Event_Tracker.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//EventRepository služi kao apstrakcija između servisa i baze podataka u sustini uradjena je enkapsulacija
public interface EventRepository extends JpaRepository<Event, Integer> {
  List<Event> findByCreatedBy_Id(Long userId);

  //ovo je nas poseban pregled tabele koji vraca event koji ima eventid i koga je kreirao korisnik sa idejm koji je zahtevan
  @Query("SELECT e FROM Event e WHERE e.id = :eventId AND e.createdBy.id = :userId")
  Event findByIdAndCreatedById(Integer eventId, Long userId);

  boolean existsByIdAndCreatedById(Integer eventId, Long userId);
}
