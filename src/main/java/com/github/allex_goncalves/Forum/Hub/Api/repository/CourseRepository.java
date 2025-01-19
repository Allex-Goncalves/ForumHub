package com.github.allex_goncalves.Forum.Hub.Api.repository;

import com.github.allex_goncalves.Forum.Hub.Api.domain.course.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Page<Course> findAllByStatusTrue(Pageable pagination);
    Optional<Course> findByName(String name);
}
