package com.github.allex_goncalves.Forum.Hub.Api.domain.response;

import com.github.allex_goncalves.Forum.Hub.Api.domain.topic.Topic;

import java.time.format.DateTimeFormatter;

public record ResponseSummaryDTO(

        Long id,
        String message,
        String date_created,
        boolean solution,
        String userName,
        String titleTopic
) {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ResponseSummaryDTO(Response response) {
        this(
                response.getId(),
                response.getMessage(),
                response.getDate_created().format(FORMATTER),
                response.getSolution(),
                response.getUser().getName(),
                response.getTopic().getTitle()
        );
    }
}
