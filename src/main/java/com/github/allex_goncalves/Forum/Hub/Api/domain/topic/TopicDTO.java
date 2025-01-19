package com.github.allex_goncalves.Forum.Hub.Api.domain.topic;

import jakarta.validation.constraints.NotBlank;

public record TopicDTO(
        @NotBlank
        String title,

        @NotBlank
        String message,

        @NotBlank
        String courseName
) {
}
