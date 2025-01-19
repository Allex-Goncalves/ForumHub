package com.github.allex_goncalves.Forum.Hub.Api.controller;

import com.github.allex_goncalves.Forum.Hub.Api.domain.topic.*;
import com.github.allex_goncalves.Forum.Hub.Api.repository.ResponseRepository;
import com.github.allex_goncalves.Forum.Hub.Api.repository.TopicRepository;
import com.github.allex_goncalves.Forum.Hub.Api.service.TopicService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("topic")
@SecurityRequirement(name = "bearer-key")
public class TopicController {

    @Autowired
    private TopicService topicService;
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ResponseRepository responseRepository;


    @PostMapping
    @Transactional
    public ResponseEntity <TopicSummaryDTO> registerTopic(@RequestBody @Valid TopicDTO data, UriComponentsBuilder uriBuilder) {
        var topicSummaryDTO = topicService.registerTopic(data);
        var uri = uriBuilder.path("/topic/{id}").buildAndExpand(topicSummaryDTO.id()).toUri();
        return ResponseEntity.created(uri).body(topicSummaryDTO);
    }

    @GetMapping("/open")
    public ResponseEntity<Page<ListTopicDTO>> listOpenTopics(@PageableDefault(size = 10) Pageable pagination) {
        var page = topicRepository.findAllByStatusOrderedByDateCreated("open", pagination)
                .map(ListTopicDTO::new); // Convertendo os tópicos para o DTO
        return ResponseEntity.ok(page);
    }

    @GetMapping("/closed")
    public ResponseEntity<Page<ListTopicDTO>> listClosedTopics(@PageableDefault(size = 10) Pageable pagination) {
        var page = topicRepository.findAllByStatusOrderedByDateCreated("closed", pagination)
                .map(ListTopicDTO::new); // Convertendo os tópicos para o DTO
        return ResponseEntity.ok(page);
    }

    @GetMapping
    public ResponseEntity <Page<ListTopicDTO>> listTopicByCourse(@RequestParam String course, @PageableDefault(size = 10) Pageable pagination){
        var page = topicRepository.findByCourseOrderedByDateCreated(course, pagination)
                .map(ListTopicDTO::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity <TopicDetailDTO> DetailTopic(@PathVariable Long id, @PageableDefault(size = 10) Pageable pageable){
        var topic = topicRepository.getReferenceById(id);
        var responses = responseRepository.findByTopicId(id, pageable);

        var topicDetailDTO = new TopicDetailDTO(topic, responses);

        return ResponseEntity.ok(topicDetailDTO);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity <TopicSummaryDTO> updateTopicStatus(@PathVariable Long id, @RequestBody @Valid TopicStatusUpdateDTO update) {
        var topic = topicRepository.getReferenceById(id);
        topic.updateTopicStatus(update);
        return ResponseEntity.ok(new TopicSummaryDTO(topic));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        var topic = topicRepository.getReferenceById(id);
        topicRepository.delete(topic);
        return ResponseEntity.noContent().build();
    }

}

