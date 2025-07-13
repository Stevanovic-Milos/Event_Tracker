package com.stevanovicm.Event_Tracker.repository;

import com.stevanovicm.Event_Tracker.entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//ovo je nas UserRepository on nasledjuje Jpa repository automatski implementira CRUD metode
public interface UserRepository extends JpaRepository<User, Integer> {
  //ovo je query metoda koja radi po konvenciji findeBy + ime polja
  Optional<User> findByUsername(String username);

  Optional<User> findById(Long id);
  //proveravamo da li postoji username da bi obavestili korisnika na frontu
  boolean existsByUsername(String username);

  //proverava postojanje emaila kako bi obavestili korisnika na frontu da je adresa zauzeta ako se vec koristi
  boolean existsByEmail(String email);
}
