package com.github.allex_goncalves.Forum.Hub.Api.service;

import com.github.allex_goncalves.Forum.Hub.Api.Infra.security.TokenService;
import com.github.allex_goncalves.Forum.Hub.Api.domain.course.Course;
import com.github.allex_goncalves.Forum.Hub.Api.domain.topic.Topic;
import com.github.allex_goncalves.Forum.Hub.Api.domain.topic.TopicDTO;
import com.github.allex_goncalves.Forum.Hub.Api.domain.topic.TopicSummaryDTO;
import com.github.allex_goncalves.Forum.Hub.Api.domain.user.User;
import com.github.allex_goncalves.Forum.Hub.Api.repository.CourseRepository;
import com.github.allex_goncalves.Forum.Hub.Api.repository.TopicRepository;
import com.github.allex_goncalves.Forum.Hub.Api.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

    @Service
    public class TopicService {

        @Autowired
        private TopicRepository topicRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private CourseRepository courseRepository;

        @Autowired
        private UserService userService;

        @Autowired
        private HttpServletRequest request;


        public TopicSummaryDTO registerTopic(TopicDTO data) {

            var user = userService.validateUser(request);

            var course = validateCourse(data.courseName());

            if (topicRepository.existsByTitleAndMessage(data.title(), data.message())) {
                throw new IllegalArgumentException("Já existe um tópico com o mesmo título e mensagem.");
            }

            var topic = new Topic(data);
            topic.setUser((User)user);
            topic.setCourse(course);
            //topicRepository.save(topic);

            return new TopicSummaryDTO(topicRepository.save(topic));
        }


        private Course validateCourse(String courseName) {
            Course course = courseRepository.findByName(courseName)
                    .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

            if (!course.getStatus()) {
                throw new RuntimeException("O curso não está ativo");
            }
            return course;
        }

//
        public void updateTopicStatusToSolution(Topic topic) {
            if (topic == null) {
                throw new IllegalStateException("O tópico associado à resposta não pode ser nulo");
            }
            topic.updateTopicStatusResponseSolution();
        }

    }
