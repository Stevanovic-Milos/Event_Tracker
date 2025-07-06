package com.stevanovicm.Event_Tracker.repository;

import com.stevanovicm.Event_Tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

//ovo je nas UserRepository on nasledjuje Jpa repository automatski implementira CRUD metode
public interface UserRepository extends JpaRepository<User, Integer> {
  //ovo je query metoda koja radi po konvenciji findeBy + ime polja
  //ako postoji vraca objekat User a ako ne null
  User findByUsername(String username);
  //ova metoda proverava postojanje korisnika sa datim username-om
  //radi po knovenciji existsBy + ime polja
  //ako korisnik postoji vraca true a ako ne false
  boolean existsByUsername(String username);
}
