package com.github.allex_goncalves.Forum.Hub.Api.service;


import com.github.allex_goncalves.Forum.Hub.Api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService implements UserDetailsService{

    @Autowired
    private UserRepository repository;

    public UserDetails loadUserByUsername() throws UsernameNotFoundException {
        return loadUserByUsername(null);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = repository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("");
        }
        return user;
    }
}
