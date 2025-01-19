package com.github.allex_goncalves.Forum.Hub.Api.domain.course;

import jakarta.validation.constraints.NotNull;

public record CourseUpdateDTO(
        @NotNull
        Long id,
        String name,
        String category
) {}
