package com.campusfix.backend.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminAccountInitializer implements CommandLineRunner {
 private final AppUserRepository users; private final PasswordEncoder encoder;
 @Value("${app.admin.email:}") private String adminEmail;
 @Value("${app.admin.password:}") private String adminPassword;
 @Value("${app.admin.name:CampusFix Administrator}") private String adminName;
 public AdminAccountInitializer(AppUserRepository users,PasswordEncoder encoder){this.users=users;this.encoder=encoder;}
 @Override public void run(String... args){
  if(adminEmail==null||adminEmail.isBlank()||adminPassword==null||adminPassword.length()<12)return;
  String email=adminEmail.trim().toLowerCase();
  AppUser user=users.findByEmailIgnoreCase(email).orElseGet(AppUser::new);
  user.setFullName(adminName==null||adminName.isBlank()?"CampusFix Administrator":adminName.trim());
  user.setEmail(email);user.setRole("ADMIN");
  // Do not reset an existing administrator password on every restart.
  if(user.getPasswordHash()==null||user.getPasswordHash().isBlank())user.setPasswordHash(encoder.encode(adminPassword));
  users.save(user);
 }
}
