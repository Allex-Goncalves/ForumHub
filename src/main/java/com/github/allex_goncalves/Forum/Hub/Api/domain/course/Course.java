package com.github.allex_goncalves.Forum.Hub.Api.domain.course;

import com.github.allex_goncalves.Forum.Hub.Api.domain.user.ProfileType;
import com.github.allex_goncalves.Forum.Hub.Api.domain.user.UserDTO;
import com.github.allex_goncalves.Forum.Hub.Api.domain.user.UserUpdateDTO;
import jakarta.persistence.*;
import lombok.*;


@Table(name = "course")
@Entity(name = "Course")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Column(nullable = false)
    private boolean status;

    public Course(CourseDTO dataCourse) {
        this.status = true;
        this.name = dataCourse.name();
        this.category = dataCourse.category();
    }


    public boolean getStatus(){return status;}

    public void updateCourseStatus(CourseStatusUpdateDTO update) {

        this.status = update.status();
    }

    public void updateInformationCourse(CourseUpdateDTO update) {
        if (update.name() != null) {
            this.name = update.name();
        }

        if (update.category() != null) {
            try {
                this.category = Category.valueOf(update.category().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid category type: " + update.category(), e);
            }
        }
    }

}

