package com.stevanovicm.Event_Tracker.entity;

import com.stevanovicm.Event_Tracker.entity.User.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "SUBSCRIPTIONS")
public class Subscription {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "USER_ID")
  private User user;

  @ManyToOne
  @JoinColumn(name = "EVENT_ID")
  private Event event;

  private LocalDateTime subscribedAt;
}
