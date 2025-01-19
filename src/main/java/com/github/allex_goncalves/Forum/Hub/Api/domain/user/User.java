package com.github.allex_goncalves.Forum.Hub.Api.domain.user;

import jakarta.persistence.*;
import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table (name = "users")
@Entity (name = "User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean status;

    @Enumerated(EnumType.STRING)
    @Column(name = "profile", nullable = false)
    private ProfileType profile;


    public User(UserDTO dataUser) {
        this.status = true;
        this.name = dataUser.name();
        this.email = dataUser.email();
        this.password = dataUser.password();
        this.profile = dataUser.profile();

    }

    public void updateInformation(UserUpdateDTO update, String encodedPassword) {
        if (update.name() != null) {
            this.name = update.name();
        }
        if (update.email() != null) {
            this.email = update.email();
        }
        if (update.password() != null) {

            this.password = encodedPassword;//passwordEncoder.encode(update.password());
        }

        if (update.profile() != null) {
            try {
                this.profile = ProfileType.valueOf(update.profile().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid profile type: " + update.profile(), e);
            }
        }
    }

    public void updateUserStatus(UserStatusUpdateDTO update){
        //if (update.status() != null) {
            this.status = update.status();
       // }
       // throw new RuntimeException("Status must be true or false");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + profile.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isEnabled() {
        return status;
    }

    public boolean getStatus(){return status;}
}

