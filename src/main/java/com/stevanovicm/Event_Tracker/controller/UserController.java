package com.stevanovicm.Event_Tracker.controller;

import com.stevanovicm.Event_Tracker.dto.UserDetailsResponse;
import com.stevanovicm.Event_Tracker.entity.User.User;
import com.stevanovicm.Event_Tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/UserDetails")
public class UserController {
  private final UserService userService;

  //na adresi /api/UserDetails poyivamo servis koji vadi sve podatke o trenutnom korisniku i vraca u frmatu naseg dto recorda UserDetailsResponse
  @GetMapping
  public ResponseEntity<UserDetailsResponse> getCurrentUserDetails(@AuthenticationPrincipal User user) {
    return ResponseEntity.ok((userService.getCurrentUserDetails(user)));
  }
}
