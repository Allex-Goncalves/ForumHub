package com.github.allex_goncalves.Forum.Hub.Api.domain.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseDTO(
        @NotBlank
        String name,
        @NotNull
        Category category
) {
}
