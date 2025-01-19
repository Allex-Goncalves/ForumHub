package com.github.allex_goncalves.Forum.Hub.Api.Infra.security;

import com.github.allex_goncalves.Forum.Hub.Api.domain.response.Response;
import com.github.allex_goncalves.Forum.Hub.Api.domain.topic.Topic;
import com.github.allex_goncalves.Forum.Hub.Api.repository.ResponseRepository;
import com.github.allex_goncalves.Forum.Hub.Api.repository.TopicRepository;
import com.github.allex_goncalves.Forum.Hub.Api.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TopicSecurityService {

    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private ResponseRepository responseRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest request;

    public boolean isTopicAuthor(Long responseId) {

        Response response = responseRepository.findById(responseId)
                .orElseThrow(() -> new EntityNotFoundException("Resposta não encontrada"));

        Topic topic = response.getTopic();

        try {

            UserDetails authenticatedUser = userService.validateUser(request);

            return topic.getUser().getEmail().equals(authenticatedUser.getUsername());
        } catch (UsernameNotFoundException e) {
            throw new IllegalStateException("Usuário não encontrado ou não autenticado");
        }
    }
}
