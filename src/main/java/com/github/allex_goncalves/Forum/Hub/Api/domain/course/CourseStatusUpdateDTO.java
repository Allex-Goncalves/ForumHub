package com.github.allex_goncalves.Forum.Hub.Api.domain.course;

import jakarta.validation.constraints.NotNull;

public record CourseStatusUpdateDTO(
        @NotNull(message = "Status cannot be null")
        Boolean status
) {}
