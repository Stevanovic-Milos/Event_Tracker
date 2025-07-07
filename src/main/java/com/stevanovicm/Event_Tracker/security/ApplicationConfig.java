package com.stevanovicm.Event_Tracker.security;

import com.stevanovicm.Event_Tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//Konfiguraciona klasa za Spring Security
//Ova klasa definiše osnovne bean-ove potrebne za autentikaciju i autorizaciju
@Configuration
//Lombok anotacija koja generiše konstruktor sa final poljima
@RequiredArgsConstructor
public class ApplicationConfig {

  //Repozitorijum za pristup korisničkim podacima iz baze
  private final UserRepository userRepository;

  //Definisanje UserDetailsService bean-a
  //Ovo je glavni servis koji Spring Security koristi za učitavanje korisničkih podataka
  @Bean
  public UserDetailsService userDetailsService() {
    //Lambda izraz koji definiše kako se nalazi korisnik po username-u
    return username -> userRepository.findByUsername(username)
      //Ako korisnik nije pronađen, baca se izuzetak koji Spring Security zna da obradi
      .orElseThrow(()-> new UsernameNotFoundException("user not found"));
  }

  //Definisanje AuthenticationProvider bean-a
  //AuthenticationProvider je glavni interfejs za autentikaciju u Spring Security-ju
  @Bean
  public AuthenticationProvider authenticationProvider(){
    //DaoAuthenticationProvider je implementacija koja koristi UserDetailsService i PasswordEncoder
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    //Postavljanje custom UserDetailsService koji koristi naš UserRepository
    authProvider.setUserDetailsService(userDetailsService());
    //Postavljanje password encoder-a za proveru lozinki
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  //Definisanje AuthenticationManager bean-a
  //AuthenticationManager je glavni interfejs za autentikaciju koji koristimo u kontrolerima
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    //Dobijanje default AuthenticationManager-a iz Spring Security konfiguracije
    return authenticationConfiguration.getAuthenticationManager();
  }

  //Definisanje PasswordEncoder bean-a
  //PasswordEncoder se koristi za hash-ovanje i proveru lozinki
  @Bean
  public PasswordEncoder passwordEncoder() {
    //BCrypt je siguran algoritam za hash-ovanje lozinki
    return new BCryptPasswordEncoder();
  }
}
