package com.stevanovicm.Event_Tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
// moramo omoguciti Async jer nam treba za mail
@EnableAsync
public class EventTrackerApplication {

  public static void main(String[] args) {
    SpringApplication.run(EventTrackerApplication.class, args);
  }
}
