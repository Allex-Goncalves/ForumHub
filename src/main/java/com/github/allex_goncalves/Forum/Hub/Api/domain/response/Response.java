package com.github.allex_goncalves.Forum.Hub.Api.domain.response;

import com.github.allex_goncalves.Forum.Hub.Api.domain.topic.Topic;
import com.github.allex_goncalves.Forum.Hub.Api.domain.topic.TopicDTO;
import com.github.allex_goncalves.Forum.Hub.Api.domain.user.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import jakarta.validation.Valid;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


@Table (name = "response")
@Entity (name = "Response")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime date_created;

    @Column(nullable = false)
    private boolean solution = false;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;


    public Response(ResponseDTO dataResponse) {
        this.message = dataResponse.message();
        this.solution = false;
    }

    public boolean getSolution(){return solution;}


    public void updateResponseSolution(@Valid ResponseSolutionUpdadeDTO update) {
        this.solution = update.solution();
    }
}
