package com.stevanovicm.Event_Tracker.service;

import com.stevanovicm.Event_Tracker.dto.UserDetailsResponse;
import com.stevanovicm.Event_Tracker.entity.User.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    //funkcija koja vraca u nasme zahtevanom formatu podatke o korisniku
    public UserDetailsResponse getCurrentUserDetails(User user) {
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
