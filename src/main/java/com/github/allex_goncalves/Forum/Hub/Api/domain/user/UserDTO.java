package com.github.allex_goncalves.Forum.Hub.Api.domain.user;

import com.github.allex_goncalves.Forum.Hub.Api.Infra.validations.PasswordValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record UserDTO(
        @NotBlank
        String name,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @PasswordValidation
        String password,
        @NotNull
        ProfileType profile
) {
}

