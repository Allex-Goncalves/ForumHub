package com.github.allex_goncalves.Forum.Hub.Api.domain.course;

public record CourseSummaryDTO(long id, String name, boolean status, Category category) {

    public CourseSummaryDTO(Course course){
        this(course.getId(), course.getName(), course.getStatus(), course.getCategory());
    }
}
