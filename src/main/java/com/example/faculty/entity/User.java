package com.example.faculty.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class User implements UserDetails {

    @Column(nullable=false)
    private String id;

    @Column(nullable = false, name="fullname")
    private String fullName;

    @Id
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable=false)
    private String birthday;

    @Column(nullable=false)
    private String password;

    @Column(nullable=false)
    private String phone;

    //role = 0 -> user; role = 1 -> admin
    @Column(nullable=false)
    private Integer role = 0;

    @Column(nullable=false)
    private String status = String.valueOf(Status.pending);

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String authority = this.role == 1 ? "ADMIN" : "USER";
        return List.of(new SimpleGrantedAuthority(authority));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.status.equals(String.valueOf(Status.accepted));
    }
}
