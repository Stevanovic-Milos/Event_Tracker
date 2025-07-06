package com.stevanovicm.Event_Tracker.security;

import com.stevanovicm.Event_Tracker.service.UserDetailesService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.*;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

// Glavni filter za JWT autentikaciju
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

  // Utility klasa za rad sa JWT tokenima
  @Autowired
  private JwtUtil jwtUtils;

  // Service za učitavanje korisničkih podataka
  @Autowired
  private UserDetailesService userDetailsService;

  // Centralna metoda za procesiranje svakog zahteva
  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    try {
      // 1. Ekstrakcija JWT tokena iz zahteva
      String jwt = parseJwt(request);

      // 2. Validacija tokena ako postoji
      if (jwt != null && jwtUtils.validateJwtToken(jwt)) {

        // 3. Dobijanje username-a iz tokena
        String username = jwtUtils.getUsernameFromToken(jwt);

        // 4. Učitavanje korisničkih podataka
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // 5. Kreiranje autentikacionog objekta
        UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(
            userDetails,
            null,  // credentials su null jer smo već autentifikovani preko JWT
            userDetails.getAuthorities()  // Dodela rola/autorizacija
          );

        // 6. Postavljanje detalja zahteva
        authentication.setDetails(
          new WebAuthenticationDetailsSource().buildDetails(request)
        );

        // 7. Čuvanje autentikacije u security kontekstu
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      // Logovanje grešaka bez prekida toka zahteva
      System.out.println("Cannot set user authentication: " + e);
    }

    // 8. Nastavljanje filter chain-a
    filterChain.doFilter(request, response);
  }

  // Pomoćna metoda za ekstrakciju JWT tokena iz header-a
  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");

    // Provera "Bearer" tokena
    if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
      // Vraćanje tokena bez "Bearer " prefiksa
      return headerAuth.substring(7);
    }
    return null;
  }
}
