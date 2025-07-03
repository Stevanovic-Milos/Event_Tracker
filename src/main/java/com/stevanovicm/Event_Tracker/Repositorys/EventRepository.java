package com.stevanovicm.Event_Tracker.Repositorys;
import com.stevanovicm.Event_Tracker.Objectsdb.Event;
import org.springframework.data.jpa.repository.JpaRepository;

//EventRepository služi kao apstrakcija između servisa i baze podataka u sustini uradjena je enkapsulacija
public interface EventRepository extends JpaRepository<Event, Integer> {
}
