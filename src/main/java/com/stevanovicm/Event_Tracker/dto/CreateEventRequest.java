package com.stevanovicm.Event_Tracker.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateEventRequest(String eventName, String artist, int availableTickets, String city, String country,
                                 String description, LocalDateTime endDate, LocalDateTime eventDate,
                                 String eventImageUrl, String genre, int minAge, String organizer,
                                 BigDecimal ticketPrice, String ticketWebsiteUrl, String venue, String eventVideoUrl) {
}
