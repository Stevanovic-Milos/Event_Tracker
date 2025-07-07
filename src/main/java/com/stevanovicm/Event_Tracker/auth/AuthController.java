package com.stevanovicm.Event_Tracker.auth;

import com.stevanovicm.Event_Tracker.dto.SigninRequest;
import com.stevanovicm.Event_Tracker.dto.SignupRequest;
import com.stevanovicm.Event_Tracker.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
//lombok biblioteka koja Generiše konstruktor koji prima sve final polja i polja sa @NonNull anotacijom
@RequiredArgsConstructor
public class AuthController {

  //dependecy injection naseg auth servisa
  private final AuthService authService;

  //bice na ruti api/auth/signup
  @PostMapping("/signup")
  //vraca odgovor u formatu AuthResponse iz naseg dto foldera
  public ResponseEntity<AuthResponse> signup(
    //ocekuje podatke u formatu Signup reques iz naseg dto foldera
    @RequestBody SignupRequest signupRequest
  ){
    // ovde ulazi u signup funkciju i prosledjuje dobijeno
    return ResponseEntity.ok(authService.signup(signupRequest));
  }

  //bice na ruti api/auth/signin
  @PostMapping("/signin")
  //vraca odgovor u formatu AuthResponse iz naseg dto foldera
  public ResponseEntity<AuthResponse> signup(
    //ocekuje podatke u formatu Signin reques iz naseg dto foldera
    @RequestBody SigninRequest signinRequest
  ){
    //ovde ulazi u nasu signin funkciju i prosledjuje dobijeno
    return ResponseEntity.ok(authService.signin(signinRequest));
  }


}
