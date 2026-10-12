package com.campusfix.backend.auth;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "app_users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, length=100) private String fullName;
    @Column(nullable=false, unique=true, length=190) private String email;
    @Column(nullable=false) private String passwordHash;
    @Column(nullable=false, length=20) private String role = "STUDENT";
    @Column(nullable=false) private LocalDateTime createdAt = LocalDateTime.now();
    public AppUser() {}
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getFullName(){return fullName;} public void setFullName(String v){fullName=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String v){passwordHash=v;}
    public String getRole(){return role;} public void setRole(String v){role=v;}
    public LocalDateTime getCreatedAt(){return createdAt;}
}
