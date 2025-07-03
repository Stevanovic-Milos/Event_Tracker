package com.stevanovicm.Event_Tracker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class EventTrackerApplication {

  public static void main(String[] args) {
    SpringApplication.run(EventTrackerApplication.class, args);
  }


  //Bean je osnovni koncept u Spring Framework-u koji predstavlja objekat kojim upravlja Spring IoC (Inversion of Control) kontejner.
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/api/**")
          //da bi front mogao da pristupi api kontroleru
          .allowedOrigins("http://localhost:4200")
          .allowedMethods("GET", "POST", "PUT", "DELETE");
      }
    };
  }
}
