package com.stevanovicm.Event_Tracker.service;

import com.stevanovicm.Event_Tracker.entity.User;
import com.stevanovicm.Event_Tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service // Spring anotacija koja označava klasu kao servisni bean
public class UserDetailesService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository; // Injektovan UserRepository za pristup bazi

  // Implementacija ključne metode iz UserDetailsService interfejsa
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    // 1. Pronalaženje korisnika u bazi po username-u
    User user = userRepository.findByUsername(username);

    // 2. Provera da li korisnik postoji
    if (user == null) {
      throw new UsernameNotFoundException("User Not Found with username: " + username);
    }

    // 3. Kreiranje UserDetails objekta koji Spring Security koristi
    return new org.springframework.security.core.userdetails.User(
      user.getUsername(), // Korisničko ime
      user.getPassword(), // Hash-ovana lozinka
      Collections.emptyList() // Lista ovlašćenja (roles) - prazna lista u ovom slučaju
    );
  }
}
