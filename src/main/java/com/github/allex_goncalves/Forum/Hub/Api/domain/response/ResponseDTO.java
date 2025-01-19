package com.github.allex_goncalves.Forum.Hub.Api.domain.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ResponseDTO(

        @NotBlank
        String message,

        @NotNull
        Long topic_id

) {
}
