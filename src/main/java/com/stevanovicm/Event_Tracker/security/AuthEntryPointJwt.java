package com.stevanovicm.Event_Tracker.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;

// Glavna klasa za obradu neuspesnih autentikacija
// Ova klasa je konfigurisana u SecurityConfig-u i svaki zahtev mora proci kroz nju
@Component  // Oznacava Spring komponentu
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

  // Centralna metoda koja se poziva pri neuspesnoj autentikaciji
  @Override
  public void commence(
    HttpServletRequest request,        // Ulazni HTTP zahtev
    HttpServletResponse response,     // HTTP odgovor za modifikaciju
    AuthenticationException authException  // Greska koja se dogodila
  ) throws IOException {
    // Postavljanje HTTP 401 statusa sa generickom porukom
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
  }
}
