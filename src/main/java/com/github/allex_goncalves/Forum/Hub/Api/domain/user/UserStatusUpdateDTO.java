package com.github.allex_goncalves.Forum.Hub.Api.domain.user;

import jakarta.validation.constraints.NotNull;

public record UserStatusUpdateDTO(
        @NotNull(message = "Status cannot be null")
        Boolean status
) {}
