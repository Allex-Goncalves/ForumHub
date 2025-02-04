package com.github.allex_goncalves.Forum.Hub.Api.repository;

import com.github.allex_goncalves.Forum.Hub.Api.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByEmail(String email);
    Page<User> findAllByStatusTrue(Pageable pagination);
}
