package com.github.allex_goncalves.Forum.Hub.Api.domain.user;

public record UserSummaryDTO(long id, String name, String email, Boolean status, ProfileType profile) {

    public UserSummaryDTO(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getStatus(), user.getProfile());
    }
}
