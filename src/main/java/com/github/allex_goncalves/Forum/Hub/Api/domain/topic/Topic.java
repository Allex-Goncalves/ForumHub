package com.github.allex_goncalves.Forum.Hub.Api.domain.topic;

import com.github.allex_goncalves.Forum.Hub.Api.domain.course.Course;
import com.github.allex_goncalves.Forum.Hub.Api.domain.response.Response;
import com.github.allex_goncalves.Forum.Hub.Api.domain.user.User;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.Valid;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Table (name = "topic")
@Entity (name = "Topic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime date_created;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    private List<Response> response;

    public Topic(TopicDTO dataTopic) {
        this.title = dataTopic.title();
        this.message = dataTopic.message();
        this.status = "open";
    }

    public void updateTopicStatus(TopicStatusUpdateDTO update) {
        this.status = update.status();
    }
    public void updateTopicStatusResponseSolution(){
        this.status = "closed";
    }
}
