package com.github.allex_goncalves.Forum.Hub.Api.service;

import com.github.allex_goncalves.Forum.Hub.Api.Infra.security.TokenService;
import com.github.allex_goncalves.Forum.Hub.Api.domain.course.Course;
import com.github.allex_goncalves.Forum.Hub.Api.domain.response.Response;
import com.github.allex_goncalves.Forum.Hub.Api.domain.response.ResponseDTO;
import com.github.allex_goncalves.Forum.Hub.Api.domain.response.ResponseSolutionUpdadeDTO;
import com.github.allex_goncalves.Forum.Hub.Api.domain.response.ResponseSummaryDTO;
import com.github.allex_goncalves.Forum.Hub.Api.domain.topic.Topic;
import com.github.allex_goncalves.Forum.Hub.Api.domain.topic.TopicDTO;
import com.github.allex_goncalves.Forum.Hub.Api.domain.topic.TopicSummaryDTO;
import com.github.allex_goncalves.Forum.Hub.Api.domain.user.User;
import com.github.allex_goncalves.Forum.Hub.Api.repository.ResponseRepository;
import com.github.allex_goncalves.Forum.Hub.Api.repository.TopicRepository;
import com.github.allex_goncalves.Forum.Hub.Api.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {


    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest request;



    public ResponseSummaryDTO registerResponse(ResponseDTO data) {

        var user = userService.validateUser(request);

        var topic = validateTopicOpen(data.topic_id());

        if (responseRepository.existsByMessage(data.message())) {
            throw new IllegalArgumentException("Já existe uma resposta com o mesmo conteúdo.");
        }

        var response = new Response(data);
        response.setUser((User)user);
        response.setTopic((Topic)topic);

        return new ResponseSummaryDTO(responseRepository.save(response));
    }


    private Topic validateTopicOpen(@NotNull Long topic_id)  {

        Topic topic = topicRepository.findById(topic_id)
                .orElseThrow(() -> new RuntimeException("Tópico não encontrado para este id"));

        if (topic.getStatus().equals("closed")) {
            throw new RuntimeException("O tópico já esta fechado");
        }
        return topic;
    }


}
