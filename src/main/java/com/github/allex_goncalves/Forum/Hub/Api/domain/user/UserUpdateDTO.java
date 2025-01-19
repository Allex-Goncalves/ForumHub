package com.github.allex_goncalves.Forum.Hub.Api.domain.user;


import jakarta.validation.constraints.NotNull;

public record UserUpdateDTO(

        @NotNull
        Long id,
        String name,
        String email,
        String password,
        String profile
) {
}
