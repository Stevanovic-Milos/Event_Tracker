package com.stevanovicm.Event_Tracker.service;

import com.stevanovicm.Event_Tracker.entity.User;
import com.stevanovicm.Event_Tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service // Spring anotacija koja označava klasu kao servisni bean
//implementiramo userDetailsService klasu radi vracanja spring security objekta
public class UserDetailesService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository; // Injektovan UserRepository za pristup bazi

  // Klasa implementira Spring Security interfejs UserService, koji ima jednu ključnu metodu: loadUserByUsername. Ova metoda je zadužena za dohvatanje korisničkih podataka na osnovu korisničkog imena tokom procesa autentikacije.
  // mi cemo ovu metodu prilagoditi nama i nasem User objektu
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    // Pronalaženje korisnika u bazi po username-u
    User user = userRepository.findByUsername(username);

    // Provera da li korisnik postoji
    if (user == null) {
      throw new UsernameNotFoundException("User Not Found with username: " + username);
    }

    // Kreiranje UserDetails objekta koji Spring Security koristi
    return new org.springframework.security.core.userdetails.User(
      user.getUsername(), // Korisničko ime
      user.getPassword(), // Hash-ovana lozinka
      Collections.emptyList() // Lista ovlašćenja (roles) - prazna lista za sada
    );
  }
}
