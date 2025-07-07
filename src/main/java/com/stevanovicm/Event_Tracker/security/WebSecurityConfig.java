package com.stevanovicm.Event_Tracker.security;

import com.stevanovicm.Event_Tracker.service.UserDetailesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration // Označava klasu kao Spring konfiguracionu klasu
public class WebSecurityConfig {

  // Servis za učitavanje korisničkih detalja
  @Autowired
  UserDetailesService userDetailsService;

  // Handler za neautorizovane zahteve, Handler za neuspešne autentikacione zahteve (vraća 401 Unauthorized)
  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  // Konfiguracija JWT token filtera, kreira i vraća custom filter za obradu JWT tokena kasnije cemo ga dodati u lanac filtera
  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  // Konfiguracija AuthenticationManager-a, glavni interfejs za autentikaciju u Spring Security, ovde ga definisemo kao bean d abi posle mogli smao da ga injektujemo u kontroler za signin
  @Bean
  public AuthenticationManager authenticationManager(
      //AuthenticationConfiguration je Spring Security klasa koja automatski kreira AuthenticationManager i povezuje ga sa UserService i PasswordEncoder. koji su ovde injektovani to radi jer prepoynaje da kalsa USerService implementira kalsu USerDetailService
    AuthenticationConfiguration authenticationConfiguration
  ) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  // Konfiguracija password encoder-a (BCrypt)
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  //Glavna security konfiguracija
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      // Isključivanje CSRF zaštite (nije potrebna za JWT)
      .csrf(csrf -> csrf.disable())

      // CORS konfiguracija
      .cors(cors -> cors.configurationSource(corsConfigurationSource()))

      // Handler za autentikacione greške, postavlja custom handler za autentikacione greške
      // Vraća lepše formatirane JSON odgovore umesto default Spring stranica
      .exceptionHandling(exceptionHandling ->
        exceptionHandling.authenticationEntryPoint(unauthorizedHandler)
      )

      // Stateless sesija (bez čuvanja stanja na serveru)
      // Ne koristi HTTP sesije (bitno za JWT)
      .sessionManagement(sessionManagement ->
        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )

      // Autorizacija zahteva
      .authorizeHttpRequests(authorizeRequests ->
        authorizeRequests
          // Dozvoljeni endpointi bez autentikacije
          .requestMatchers("/api/auth/**").permitAll()
          // Svi ostali zahtevi zahtevaju autentikaciju
          .anyRequest().authenticated()
      );

    // Dodavanje custom JWT filtera
    http.addFilterBefore(
      //ovde smo dodali nas kreirani filte u lanac filtera odmah iznad UsernamePasswordAuthenticationFilter
      authenticationJwtTokenFilter(),
      // funkcija addFilterBefore ocekuje kao prvi objekat instancu filtera koji stavljamo a kao drugu ocekuje klasu pre koje stavljamo filter
      UsernamePasswordAuthenticationFilter.class
    );
    //Poziv http.build() završava konfiguraciju HttpSecurity objekta i kreira SecurityFilterChain bean, koji je Spring Security koristi za primenu sigurnosnih pravila na dolazeće HTTP zahteve.
    return http.build();
  }

  // CORS konfiguracija
  //CORS (Cross-Origin Resource Sharing) je sigurnosni mehanizam u pregledačima koji kontroliše da li JavaScript na jednoj web stranici ( frontend-u na http://localhost:4200) može da šalje zahteve ka serveru na drugom domenu ( backend-u na http://localhost:8080).
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    // Dozvoljeni origin (frontend aplikacija)
    configuration.setAllowedOrigins(List.of("http://localhost:4200"));
    // Dozvoljene HTTP metode
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    // Dozvoljeni svi header-i
    configuration.setAllowedHeaders(List.of("*"));
    // Dozvoljeno slanje kredencijala (npr. cookies)
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    // Primena konfiguracije na sve putanje
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
