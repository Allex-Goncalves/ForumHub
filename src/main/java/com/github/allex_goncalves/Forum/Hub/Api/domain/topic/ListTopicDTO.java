package com.github.allex_goncalves.Forum.Hub.Api.domain.topic;

import java.time.format.DateTimeFormatter;

public record ListTopicDTO(
        String title,
        String message,
        String date_created,
        String status,
        String userName,
        String courseName
) {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public ListTopicDTO(Topic topic) {
        this(
                topic.getTitle(),
                topic.getMessage(),
                topic.getDate_created().format(FORMATTER),
                topic.getStatus(),
                topic.getUser().getName(),
                topic.getCourse().getName()
        );
    }
}
