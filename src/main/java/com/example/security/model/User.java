package com.example.security.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int role;
    private String otp;
    private LocalDateTime otp_expires_in;
    private String password;
    private String email;
    private int failed_logins = 0;
    private long customer_id;
    private boolean status = true;
    private LocalDateTime last_login;
    private LocalDateTime created_at;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // list of roles
        return List.of(new SimpleGrantedAuthority("operator"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return status;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status;
    }

    @Override
    public boolean isEnabled() {
        return status;
    }
}
