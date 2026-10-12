package com.campusfix.backend.auth;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
 private final SecretKey key; private final long expirationMs;
 public JwtService(@Value("${app.jwt.secret}") String secret,@Value("${app.jwt.expiration-ms:86400000}") long expirationMs){
  if(secret.getBytes(StandardCharsets.UTF_8).length<32) throw new IllegalStateException("APP_JWT_SECRET must contain at least 32 characters");
  this.key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); this.expirationMs=expirationMs;
 }
 public String create(AppUser user){long now=System.currentTimeMillis();return Jwts.builder().subject(user.getEmail()).claim("role",user.getRole()).issuedAt(new Date(now)).expiration(new Date(now+expirationMs)).signWith(key).compact();}
 public Claims parse(String token){return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();}
 public boolean valid(String token){try{return parse(token).getExpiration().after(new Date());}catch(Exception e){return false;}}
}
