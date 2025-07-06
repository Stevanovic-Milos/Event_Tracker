package com.stevanovicm.Event_Tracker.entity;

import jakarta.persistence.*;
import lombok.*;

// generiše gettere, settere, toString, equals, hashCode
@Data
// mora eksplicitno jer @Data ovo ne generiše ovo je prazan konstruktor
@NoArgsConstructor
// mora eksplicitno jer @Data generiše samo required args ovo je kosntruktor sa svim poljima
@AllArgsConstructor
// Označava da je ova klasa JPA entitet (mapira se na tabelu u bazi)
@Entity
// Eksplicitno definiše naziv tabele u bazi
@Table(name = "USERS")
public class User {
  @Id // Označava primarni ključ
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment u bazi
  private Long id;

  @Column(name = "USERNAME",unique = true) // Kolona sa unique constraint-om
  private String username;

  @Column(name = "PASSWORD")
  private String password; // Obična kolona bez dodatnih ograničenja
}
