package com.github.allex_goncalves.Forum.Hub.Api.controller;

import com.github.allex_goncalves.Forum.Hub.Api.domain.course.CourseSummaryDTO;
import com.github.allex_goncalves.Forum.Hub.Api.domain.response.Response;
import com.github.allex_goncalves.Forum.Hub.Api.domain.response.ResponseDTO;
import com.github.allex_goncalves.Forum.Hub.Api.domain.response.ResponseSolutionUpdadeDTO;
import com.github.allex_goncalves.Forum.Hub.Api.domain.response.ResponseSummaryDTO;
import com.github.allex_goncalves.Forum.Hub.Api.domain.topic.ListTopicDTO;
import com.github.allex_goncalves.Forum.Hub.Api.repository.ResponseRepository;
import com.github.allex_goncalves.Forum.Hub.Api.repository.TopicRepository;
import com.github.allex_goncalves.Forum.Hub.Api.service.ResponseService;
import com.github.allex_goncalves.Forum.Hub.Api.service.TopicService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("response")
@SecurityRequirement(name = "bearer-key")
public class ResponseController {

    @Autowired
    private ResponseRepository responseRepository;
    @Autowired
    TopicRepository topicRepository;
    @Autowired
    private ResponseService responseService;
    @Autowired
    private TopicService topicService;

    @PostMapping
    @Transactional
    public ResponseEntity <ResponseSummaryDTO> registerResponse(@RequestBody @Valid ResponseDTO data, UriComponentsBuilder uriBuilder) {
        var responseSummaryDTO = responseService.registerResponse(data);
        var uri = uriBuilder.path("/response/{id}").buildAndExpand(responseSummaryDTO.id()).toUri();
        return ResponseEntity.created(uri).body(responseSummaryDTO);
    }
    @PreAuthorize("hasRole('MANAGER') or @topicSecurityService.isTopicAuthor(#id)")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity <ResponseSummaryDTO> updateResponseSolution(@PathVariable Long id, @RequestBody @Valid ResponseSolutionUpdadeDTO update) {
        Response response = responseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Esta resposta não existe"));

        if(response.getTopic().getStatus().equals("closed")){
            throw new RuntimeException("O tópico já esta fechado");
        }

        if(update.solution()){
            topicService.updateTopicStatusToSolution(response.getTopic());
        }
        response.updateResponseSolution(update);
        return ResponseEntity.ok(new ResponseSummaryDTO(response));
    }

}
