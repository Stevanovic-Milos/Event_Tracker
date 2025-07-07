package com.stevanovicm.Event_Tracker.dto;

//ovo cemo koristiti kao standardizovan odgovr servera za auth gde ce token biti i nasa custom poruka
public record AuthResponse(String token, boolean success) {}
