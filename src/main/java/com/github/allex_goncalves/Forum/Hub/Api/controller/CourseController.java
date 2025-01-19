package com.github.allex_goncalves.Forum.Hub.Api.controller;

import com.github.allex_goncalves.Forum.Hub.Api.domain.course.*;
import com.github.allex_goncalves.Forum.Hub.Api.repository.CourseRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("course")
@SecurityRequirement(name = "bearer-key")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping
    @Transactional
    public ResponseEntity <CourseSummaryDTO> registerCourse(@RequestBody @Valid CourseDTO data, UriComponentsBuilder uriBuilder) {

        var course = new Course(data);
        courseRepository.save(course);
        var uri = uriBuilder.path("/course/{id}").buildAndExpand(course.getId()).toUri();
        return ResponseEntity.created(uri).body(new CourseSummaryDTO(course));
    }

    @GetMapping
    public ResponseEntity<Page<CourseSummaryDTO>> list(@PageableDefault(size = 10, sort = {"name"}) Pageable pagination) {
        var page = courseRepository.findAllByStatusTrue(pagination).map(CourseSummaryDTO::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity <CourseSummaryDTO> detailCourse(@PathVariable Long id) {
        var course = courseRepository.getReferenceById(id);
        return ResponseEntity.ok(new CourseSummaryDTO(course));
    }

    @PutMapping
    @Transactional
    public ResponseEntity <CourseSummaryDTO> updateCourse(@RequestBody @Valid CourseUpdateDTO courseUpdate) {
        try {
            var course = courseRepository.getReferenceById(courseUpdate.id());
            course.updateInformationCourse(courseUpdate);
            return ResponseEntity.ok(new CourseSummaryDTO(course));

        } catch (EntityNotFoundException e) {
            throw new RuntimeException("Course not found"+ courseUpdate.id(), e);
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity <CourseSummaryDTO> updateCourseStatus(@PathVariable Long id, @RequestBody @Valid CourseStatusUpdateDTO update) {
        var course = courseRepository.getReferenceById(id);
        course.updateCourseStatus(update);
        return ResponseEntity.ok(new CourseSummaryDTO(course));
    }

}
