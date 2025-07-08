package com.stevanovicm.Event_Tracker.entity.User;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

// generiše gettere, settere, toString, equals, hashCode
@Data
@Builder
// mora eksplicitno jer @Data ovo ne generiše ovo je prazan konstruktor
@NoArgsConstructor
// mora eksplicitno jer @Data generiše samo required args ovo je kosntruktor sa svim poljima
@AllArgsConstructor
// Označava da je ova klasa JPA entitet (mapira se na tabelu u bazi)
@Entity
// Eksplicitno definiše naziv tabele u bazi
@Table(name = "USERS")

public class User implements UserDetails {
  @Id // Označava primarni ključ
  @GeneratedValue(strategy = GenerationType.IDENTITY)// Auto-increment u bazi
  private Long id;

  private String username;

  private String password; // Obična kolona bez dodatnih ograničenja

  private String firstname;

  private String lastname;

  private String email;

  @Enumerated(EnumType.STRING)
  private UserRoles role;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }
  @Override
  public boolean isEnabled() {
    return true;
  }
}
