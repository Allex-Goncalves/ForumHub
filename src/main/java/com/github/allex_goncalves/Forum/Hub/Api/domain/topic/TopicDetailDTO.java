package com.github.allex_goncalves.Forum.Hub.Api.domain.topic;
import com.github.allex_goncalves.Forum.Hub.Api.domain.response.ListResponseDTO;
import com.github.allex_goncalves.Forum.Hub.Api.domain.response.Response;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public record TopicDetailDTO(
        Long id,
        String title,
        String message,
        String date_created,
        String status,
        String userName,
        String courseName,
        Page<ListResponseDTO> responses
) {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public TopicDetailDTO(Topic topic, Page<Response> responses) {
        this(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getDate_created().format(FORMATTER),
                topic.getStatus(),
                topic.getUser().getName(),
                topic.getCourse().getName(),
                responses.map(ListResponseDTO::new)
        );
    }
}
