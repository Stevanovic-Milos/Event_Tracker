package com.stevanovicm.Event_Tracker.dto;


//format odgovora logovanja u aplikaciji
public record SignInResponse (String token, boolean success) {
}
