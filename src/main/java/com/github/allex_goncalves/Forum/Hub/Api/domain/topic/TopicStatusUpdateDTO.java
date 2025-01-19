package com.github.allex_goncalves.Forum.Hub.Api.domain.topic;

import jakarta.validation.constraints.NotBlank;

public record TopicStatusUpdateDTO(
        @NotBlank(message = "Status cannot be null")
        String status
) {
        public TopicStatusUpdateDTO(String status) {

                String normalizedStatus = status.toLowerCase().trim();

                if (!"open".equals(normalizedStatus) && !"closed".equals(normalizedStatus)) {
                        throw new IllegalArgumentException("Status must be either 'open' or 'closed'");
                }
                this.status = normalizedStatus;
        }
}
