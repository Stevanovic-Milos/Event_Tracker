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

  // Handler za neautorizovane zahteve
  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  // 1. Konfiguracija JWT token filtera
  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  // 2. Konfiguracija AuthenticationManager-a
  @Bean
  public AuthenticationManager authenticationManager(
    AuthenticationConfiguration authenticationConfiguration
  ) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  // 3. Konfiguracija password encoder-a (BCrypt)
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // 4. Glavna security konfiguracija
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      // Isključivanje CSRF zaštite (nije potrebna za JWT)
      .csrf(csrf -> csrf.disable())

      // CORS konfiguracija
      .cors(cors -> cors.configurationSource(corsConfigurationSource()))

      // Handler za autentikacione greške
      .exceptionHandling(exceptionHandling ->
        exceptionHandling.authenticationEntryPoint(unauthorizedHandler)
      )

      // Stateless sesija (bez čuvanja stanja na serveru)
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
      authenticationJwtTokenFilter(),
      UsernamePasswordAuthenticationFilter.class
    );

    return http.build();
  }

  // 5. CORS konfiguracija
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
