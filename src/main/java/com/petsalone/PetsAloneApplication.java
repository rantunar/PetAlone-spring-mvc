package com.petsalone;

import com.petsalone.entity.User;
import com.petsalone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PetsAloneApplication {

  @Autowired private UserRepository userRepository;

  public static void main(String[] args) {
    SpringApplication.run(PetsAloneApplication.class, args);
  }

  @EventListener
  public void SeedData(ContextRefreshedEvent event) {
    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    User user = new User();
    user.setUsername("admin");
    user.setPassword(encoder.encode("admin"));
    user.setEmail("elmyraduff@petsalone.com");
    userRepository.save(user);
  }
}
