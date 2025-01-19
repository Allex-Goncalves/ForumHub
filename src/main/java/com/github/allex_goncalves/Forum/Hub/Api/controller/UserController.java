package com.github.allex_goncalves.Forum.Hub.Api.controller;

import com.github.allex_goncalves.Forum.Hub.Api.domain.user.*;
import com.github.allex_goncalves.Forum.Hub.Api.repository.UserRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("user")
@SecurityRequirement(name = "bearer-key")
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @Transactional
    public ResponseEntity registerUser(@RequestBody @Valid UserDTO data, UriComponentsBuilder uriBuilder) {
        String hashedPassword = passwordEncoder.encode(data.password());
        var user = new User(data);
        user.setPassword(hashedPassword);
        repository.save(user);

        var uri = uriBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(new UserSummaryDTO(user));
    }

    @GetMapping
    public ResponseEntity<Page<ListUserDTO>> list(@PageableDefault(size = 10, sort = {"name"}) Pageable pagination) {
        var page = repository.findAllByStatusTrue(pagination).map(ListUserDTO::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity <UserSummaryDTO>updateUser(@RequestBody @Valid UserUpdateDTO userUpdate) {
        try {
            var user = repository.getReferenceById(userUpdate.id());
            if (userUpdate.password() != null) {
                String encodedPassword = passwordEncoder.encode(userUpdate.password());
                user.updateInformation(userUpdate, encodedPassword);
            } else {
                user.updateInformation(userUpdate, null);
            }
            return ResponseEntity.ok(new UserSummaryDTO(user));
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("User not found"+ userUpdate.id(), e);
        }
    }
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity <UserSummaryDTO>updateUserStatus(@PathVariable Long id, @RequestBody @Valid UserStatusUpdateDTO update) {
        var user = repository.getReferenceById(id);
        user.updateUserStatus(update);
        return ResponseEntity.ok(new UserSummaryDTO(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity <UserSummaryDTO> detailUser(@PathVariable Long id) {
        var user = repository.getReferenceById(id);
        return ResponseEntity.ok(new UserSummaryDTO(user));
    }
}
