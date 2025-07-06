package com.stevanovicm.Event_Tracker.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

// Komponenta za rad sa JWT tokenima
@Component
public class JwtUtil {

  // Vrednosti iz application.properties
  @Value("${jwt.secret}")  // Tajni ključ za potpisivanje tokena
  private String jwtSecret;

  @Value("${jwt.expiration}")  // Vreme trajanja tokena u milisekundama
  private int jwtExpirationMs;

  private SecretKey key;  // Kriptografski ključ za potpisivanje

  // Inicijalizacija ključa nakon konstrukcije objekta
  @PostConstruct
  public void init() {
    this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
  }

  // Generisanje JWT tokena
  public String generateToken(String username) {
    return Jwts.builder()
      .setSubject(username)  // Postavljanje subjekta (obično username)
      .setIssuedAt(new Date())  // Vreme kreiranja
      .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))  // Vreme isteka
      .signWith(key, SignatureAlgorithm.HS256)  // Potpisivanje tokena
      .compact();  // Konverzija u string
  }

  // Dobijanje username-a iz tokena
  public String getUsernameFromToken(String token) {
    return Jwts.parserBuilder()
      .setSigningKey(key).build()  // Postavljanje ključa za verifikaciju
      .parseClaimsJws(token)  // Parsiranje tokena
      .getBody()  // Dobijanje sadržaja (claims)
      .getSubject();  // Dobijanje subjekta (username)
  }

  // Validacija JWT tokena
  public boolean validateJwtToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;  // Token je validan
    } catch (SecurityException e) {
      System.out.println("Invalid JWT signature: " + e.getMessage());
    } catch (MalformedJwtException e) {
      System.out.println("Invalid JWT token: " + e.getMessage());
    } catch (ExpiredJwtException e) {
      System.out.println("JWT token is expired: " + e.getMessage());
    } catch (UnsupportedJwtException e) {
      System.out.println("JWT token is unsupported: " + e.getMessage());
    } catch (IllegalArgumentException e) {
      System.out.println("JWT claims string is empty: " + e.getMessage());
    }
    return false;  // Token nije validan
  }
}
