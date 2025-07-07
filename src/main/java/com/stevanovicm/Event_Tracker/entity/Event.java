package com.stevanovicm.Event_Tracker.entity;

import jakarta.persistence.*;
import lombok.Data;

//Označava da je ova klasa JPA entitet (tj. mapira se na tabelu u bazi) event auto-ddl iz proprties automatski mapira i zna da treba da radi sa ovom kalsom.
@Entity
//nema potrebe za geterima i seterema lobok automatski zavrsi get i set metode za sva polja
@Data
//Eksplicitno definiše naziv tabele u bazi (u ovom slučaju Event).
@Table(name = "EVENTS")

public class Event {

    //deklarisali smo kolonu id nase tabele identifikator @id automatski ga pravi da bude jedinstveno
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment u bazi
    private int id;

    private String eventName;
}
