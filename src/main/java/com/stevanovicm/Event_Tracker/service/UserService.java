package com.stevanovicm.Event_Tracker.service;

import com.stevanovicm.Event_Tracker.dto.UserDetailsResponse;
import com.stevanovicm.Event_Tracker.entity.User.User;
import com.stevanovicm.Event_Tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //funkcija koja vraca u nasme zahtevanom formatu podatke o korisniku
    public UserDetailsResponse getCurrentUserDetails() {
      //izvlacimo username korisnika preko spring sekjutritija(promeniti metodu standardizovati sa ostalim da ide prkeo ida-a i laks eje da se prosledi direkt iz kontrolera)
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        //vadimo korisnika dodati
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Korisnik nije pronadjen"));

        //Vracamo korisnika tacno onako kao record zahteva samo ta polja
        return new UserDetailsResponse(
                user.getUsername(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getRole().toString()
        );
    }
}
