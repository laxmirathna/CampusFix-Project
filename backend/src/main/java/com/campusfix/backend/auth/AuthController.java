package com.campusfix.backend.auth;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.security.Principal;
import java.util.Locale;

@RestController @RequestMapping("/api/auth")
public class AuthController {
 private final AppUserRepository users; private final PasswordEncoder encoder; private final JwtService jwt;
 public AuthController(AppUserRepository users,PasswordEncoder encoder,JwtService jwt){this.users=users;this.encoder=encoder;this.jwt=jwt;}
 @PostMapping("/register") @ResponseStatus(HttpStatus.CREATED)
 public AuthDtos.AuthResponse register(@Valid @RequestBody AuthDtos.RegisterRequest req){
  String email=req.email().trim().toLowerCase(Locale.ROOT); if(users.existsByEmailIgnoreCase(email))throw new ResponseStatusException(HttpStatus.CONFLICT,"An account with this email already exists");
  AppUser u=new AppUser();u.setFullName(req.fullName().trim());u.setEmail(email);u.setPasswordHash(encoder.encode(req.password()));u.setRole("STUDENT");u=users.save(u);return response(u);
 }
 @PostMapping("/login") public AuthDtos.AuthResponse login(@Valid @RequestBody AuthDtos.LoginRequest req){
  AppUser u=users.findByEmailIgnoreCase(req.email().trim()).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid email or password"));
  if(!encoder.matches(req.password(),u.getPasswordHash()))throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid email or password");return response(u);
 }
 @GetMapping("/me") public AuthDtos.UserView me(Principal principal){AppUser u=users.findByEmailIgnoreCase(principal.getName()).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED));return view(u);}
 private AuthDtos.AuthResponse response(AppUser u){return new AuthDtos.AuthResponse(jwt.create(u),view(u));}
 private AuthDtos.UserView view(AppUser u){return new AuthDtos.UserView(u.getId(),u.getFullName(),u.getEmail(),u.getRole());}
}
