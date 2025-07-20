package com.stevanovicm.Event_Tracker.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class EmailSenderService {
  private final JavaMailSender javaMailSender;
  

    // asinhrona da bi se drugi thread bavio njome dok glavni thred obradjuje ostale operacije
  @Async
  public void sendEmail(String to, String subject, String body) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject(subject);
    message.setText(body);
    javaMailSender.send(message);

    System.out.println("Poslato " + to);
  }
}
