package com.stevanovicm.Event_Tracker.service;

import com.stevanovicm.Event_Tracker.dto.UserDetailsResponse;
import com.stevanovicm.Event_Tracker.entity.User.User;
import com.stevanovicm.Event_Tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public UserDetailsResponse getCurrentUserDetails() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    // Fetch the user from the repository
    User user = userRepository.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    // Map the User entity to UserDetailsResponse
    return new UserDetailsResponse(
      user.getUsername(),
      user.getFirstname(),
      user.getLastname(),
      user.getEmail()
    );
  }
}
