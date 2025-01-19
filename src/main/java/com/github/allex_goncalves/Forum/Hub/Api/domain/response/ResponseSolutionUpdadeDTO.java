package com.github.allex_goncalves.Forum.Hub.Api.domain.response;

import jakarta.validation.constraints.NotNull;

public record ResponseSolutionUpdadeDTO(
        @NotNull(message = "Status cannot be null")
        Boolean solution
) {}
