package com.stevanovicm.Event_Tracker.dto;

//podaci koje ocekujemo na sign-up
public record SignupRequest(String username, String password, String email, String firstname, String lastname) {}
