package com.github.allex_goncalves.Forum.Hub.Api.repository;

import com.github.allex_goncalves.Forum.Hub.Api.domain.topic.Topic;


import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface TopicRepository extends JpaRepository<Topic, Long> {
    @Query("SELECT t FROM Topic t WHERE t.status = :status ORDER BY t.date_created DESC")
    Page<Topic> findAllByStatusOrderedByDateCreated(String status, Pageable pageable);

    @Query("SELECT COUNT(t) > 0 FROM Topic t WHERE t.title = :title AND t.message = :message")
    boolean existsByTitleAndMessage(@NotBlank String title, @NotBlank String message);

    @Query("SELECT t FROM Topic t WHERE t.course.name = :course ORDER BY t.date_created DESC")
    Page<Topic> findByCourseOrderedByDateCreated(@NotBlank String course, Pageable pageable);

}
