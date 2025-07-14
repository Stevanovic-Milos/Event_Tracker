package com.stevanovicm.Event_Tracker.entity;

import com.stevanovicm.Event_Tracker.entity.User.User;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "EVENTS")
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String eventName;
    private String artist;
    private int availableTickets;
    private String city;
    private String country;
    private String description;
    private LocalDateTime endDate;
    private LocalDateTime eventDate;
    private String eventImageUrl;
    private String genre;
    private boolean isSoldOut;
    private int minAge;
    private String organizer;
    private BigDecimal ticketPrice;
    private String ticketWebsiteUrl;
    private String venue;
    private String eventVideoUrl;
    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

}
