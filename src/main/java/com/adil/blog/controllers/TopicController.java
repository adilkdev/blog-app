package com.adil.blog.controllers;

import com.adil.blog.payloads.ApiResponse;
import com.adil.blog.payloads.TopicDto;
import com.adil.blog.services.TopicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    @Autowired
    TopicService topicService;

    @PostMapping("/")
    public ResponseEntity<TopicDto> createTopic(@Valid @RequestBody TopicDto topicDto) {
        TopicDto createTopicDto = topicService.createTopic(topicDto);
        return new ResponseEntity<>(createTopicDto, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<TopicDto> updateTopic(@Valid @RequestBody TopicDto topicDto) {
        TopicDto updateTopicDto = topicService.updateTopic(topicDto);
        return new ResponseEntity<>(updateTopicDto, HttpStatus.OK);
    }

    @DeleteMapping("/{topicId}")
    public ResponseEntity<ApiResponse> deleteTopic(@PathVariable Integer topicId) {
        topicService.deleteTopic(topicId);
        return new ResponseEntity<>(new ApiResponse("Topic is deleted successfully!", true), HttpStatus.OK);
    }

    @GetMapping("/{topicId}")
    public ResponseEntity<TopicDto> getTopicById(@PathVariable Integer  topicId) {
        TopicDto topicDto = topicService.getTopicById(topicId);
        return new ResponseEntity<>(topicDto, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<TopicDto>> getTopics() {
        return new ResponseEntity<>(topicService.getTopics(), HttpStatus.OK);
    }

}
