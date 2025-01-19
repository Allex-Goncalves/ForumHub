package com.github.allex_goncalves.Forum.Hub.Api.domain.response;

import java.time.format.DateTimeFormatter;

public record ListResponseDTO(
        Long id,
        String message,
        String date_created,
        boolean solution,
        String userName
) {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public ListResponseDTO(Response response) {
        this(
                response.getId(),
                response.getMessage(),
                response.getDate_created().format(FORMATTER),
                response.getSolution(),
                response.getUser().getName()
        );
    }
}
