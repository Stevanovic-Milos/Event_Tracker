package com.stevanovicm.Event_Tracker.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

//Glavni filter za JWT autentikaciju naslednik OnecPerRequestFilter klase sto znaci da ce se uvek jednom izvrsiti u okviru zahteva odavde koristimo dogilter funkciju
//zbog konfiguracije spring securitya ova klasa kao nastavik filter klase i zbog zadate konfiguracije u SecurityConfig-u ce se automatski izvrsavati za svaki zahtev
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

  //Utility klasa za rad sa JWT tokenima
  @Autowired
  private JwtUtil jwtUtils;

  //Service za učitavanje korisničkih podataka
  @Autowired
  private UserDetailsService userDetailsService;

  //Centralna metoda za procesiranje svakog zahteva
  //Ovo je ključna metoda koju moras implementirati kada nasleđujes OncePerRequestFilter. Ona sadrž glavnu logiku vašeg filtera.
  @Override
  protected void doFilterInternal(
    HttpServletRequest request, //Sadrži sve informacije o zahtevu (headere, parametre, body itd.)
    HttpServletResponse response, //HTTP odgovor koji možete modifikovati
    FilterChain filterChain // ovo je lanac filtera koji treba nastaviti, obavezno pozvati filterChain.doFilter() da zahtev prođe dalje
  ) throws ServletException, IOException {
    try {
      //Ekstrakcija JWT tokena iz zahteva
      String jwt = parseJwt(request);

      //Validacija tokena ako postoji
      if (jwt != null && jwtUtils.validateJwtToken(jwt)) {

        //Dobijanje username-a iz tokena pozivanjem funkcije iz jwtUtil kalse
        String username = jwtUtils.getUsernameFromToken(jwt);

        //Učitavanje korisničkih podataka u nas UserDetails objekat
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        //Kreiranje autentikacionog objekta kreira se na osnovu podataka iz baze izvucenih iz jwt tokena
        UsernamePasswordAuthenticationToken authentication =
          //pravimo novi token za authentifikaciju
          new UsernamePasswordAuthenticationToken(
            userDetails, //ovo su svi podaci o korinku username password itd
            null,  // credentials su null jer smo već autentifikovani preko JWT
            userDetails.getAuthorities()  // Dodela rola/autorizacija
          );

        //Postavljanje detalja zahteva, dodaje informacije o IP adresi, session ID itd.
        authentication.setDetails(
          new WebAuthenticationDetailsSource().buildDetails(request)
        );

        //Čuvanje autentikacije u security kontekstu
        //Omogućava drugim delovima aplikacije da pristupe korisničkim podacima kroz: SecurityContextHolder.getContext().getAuthentication()
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      //Logovanje grešaka bez prekida toka zahteva
      System.out.println("Cannot set user authentication: " + e);
    }

    //Nastavljanje filter chain-a
    //Prosleđuje zahtev dalje kroz lanac filtera cak i ako auth nije validan drugi deo koda je zaduzen za obradu neuspelog zahteva
    filterChain.doFilter(request, response);
  }

  //Pomoćna metoda za ekstrakciju JWT tokena iz header-a
  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");

    //Provera "Bearer" tokena
    if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
      //Vraćanje tokena bez "Bearer " prefiksa
      return headerAuth.substring(7);
    }
    return null;
  }
}
