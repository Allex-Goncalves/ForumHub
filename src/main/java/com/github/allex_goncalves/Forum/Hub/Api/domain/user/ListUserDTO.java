package com.github.allex_goncalves.Forum.Hub.Api.domain.user;

public record ListUserDTO(String name, String email, Boolean status, ProfileType profile) {
    public ListUserDTO(User user) {
        this(user.getName(), user.getEmail(), user.getStatus(), user.getProfile());
    }
}
