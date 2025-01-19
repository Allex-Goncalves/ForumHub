package com.github.allex_goncalves.Forum.Hub.Api.repository;

import com.github.allex_goncalves.Forum.Hub.Api.domain.response.Response;

import com.github.allex_goncalves.Forum.Hub.Api.domain.user.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface ResponseRepository extends JpaRepository<Response, Long> {

    @Query("SELECT r FROM Response r WHERE r.topic.id = :topicId")
    Page<Response> findByTopicId(Long topicId, Pageable pageable);

    @Query("SELECT COUNT(t) > 0 FROM Response t WHERE t.message = :message")
    boolean existsByMessage(@NotBlank String message);

}
