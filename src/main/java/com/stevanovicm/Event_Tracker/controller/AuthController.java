package com.stevanovicm.Event_Tracker.controller;

import com.stevanovicm.Event_Tracker.dto.Response;
import com.stevanovicm.Event_Tracker.dto.SignInResponse;
import com.stevanovicm.Event_Tracker.entity.User;
import com.stevanovicm.Event_Tracker.repository.UserRepository;
import com.stevanovicm.Event_Tracker.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

//ovo je springov kontroler koji govori da je cela ova klasa restApi
@RestController

//ovde dodeljujemo defaultni maping za rute za signin(logvoanje) i signup(registraciju)
@RequestMapping("/api/auth")
public class AuthController {

  //radimo dependency injection authenticationManagera --Glavni interfejs Spring Security-a za autentifikaciju.
  @Autowired
  AuthenticationManager authenticationManager;

  //radimo dependency injection UserRepository --Moj interfejs koji je naslednik Jpa klase sa metodom za nalazenje po username
  @Autowired
  UserRepository userRepository;

  //radimo dependency injection PasswordEncoder --Interfejs za šifrovanje lozinki (BCrypt implementacija).
  @Autowired
  PasswordEncoder encoder;

  //radimo dependency injection JwtUtil --Pomocna klasa za rad sa JWT tokenima.
  @Autowired
  JwtUtil jwtUtils;

  //Postavljamo /signin kao endpoint ove metode
  //da bismo sa fronta pristupili njoj treba gadjati api/auth/signin
  @PostMapping("/signin")
  //ova klasa ce vratiti nas response u json formatu kako ga front i ocekuje
  public ResponseEntity<SignInResponse> authenticateUser(@RequestBody User user) {
    //AuthenticationManager je springova klasa koja je glavna za svaku autentifikaciju
    Authentication authentication = authenticationManager.authenticate(
      //ovde se formira token od podataka koje metoda dobije i taj token sa username i password se trazi u bazi
      //ako nema poklapanja korisnik ne psotoji i vraca se error
      new UsernamePasswordAuthenticationToken(
        user.getUsername(),
        user.getPassword()
      )
    );

    //user details je defaul springova klasa koja vraca osnovne podatke o korisniku
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    //preko poziva na userDetails zovemo funkciju koja nam vraca username i taj username prosledjujemo nasoj funkciji za kreiranje jwt tokena
    String token = jwtUtils.generateToken(userDetails.getUsername());

    //ako sve prodje serveru vracamo dto format sa SignInResponse sa tokenom i sucessom: true
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(new SignInResponse(token, true));
  }

  // Definiše POST endpoint na putanji "/signup" koja se dodaje na bazni URL "/api/auth"
  @PostMapping("/signup")
  public ResponseEntity<Response> registerUser(@RequestBody User user) {
    // proveravamo da li user postoji u bazi
    if (userRepository.existsByUsername(user.getUsername())) {
      //ako postoji vracamo konflikt (409) i Response iz dto sa porukom i sucess statusom false
      return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(new Response("Username is already taken", false));
    }

    // kreiramo novog korisnika
    User newUser = new User(
      null, //baza sama kreira id
      user.getUsername(), //postavljamo za username prosledjeni username
      encoder.encode(user.getPassword()) //kodiramo sifru pre upisa u bazu
    );
    //cuvamo korisnika u bazu
    userRepository.save(newUser);

    //vracamo status created (201)
    return ResponseEntity
      .status(HttpStatus.CREATED)
      //poruka je d aje uspesno registrovan a status sucess true
      .body(new Response("User registered successfully", true));
  }
}
