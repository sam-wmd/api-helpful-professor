package com.sam.apihelpfulprofessor.controller;


import com.sam.apihelpfulprofessor.dto.ExampleDto;
import com.sam.apihelpfulprofessor.dto.TopicDto;
import com.sam.apihelpfulprofessor.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TopicController {

    TopicService topicService;
    @Autowired
    TopicController(TopicService topicService){
        this.topicService = topicService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TopicDto>> getAllTopics(){
        List<TopicDto> topics = topicService.getAllTopics();
        return ResponseEntity.ok(topics);
    }


    @GetMapping("/{title}")
    public ResponseEntity<TopicDto> get(@PathVariable String title){
        TopicDto topicDto = topicService.findTopicByTitle(title);
        return ResponseEntity.ok(topicDto);
    }

    @GetMapping("/{title}/examples")
    public ResponseEntity<List<ExampleDto>> getExamples(@PathVariable String title){
        TopicDto topicDto = topicService.findTopicByTitle(title);
        return ResponseEntity.ok(topicDto.examples());
    }

    @GetMapping("/{title}/definition")
    public ResponseEntity<String> getDefinition(@PathVariable String title){
        TopicDto topicDto = topicService.findTopicByTitle(title);
        return ResponseEntity.ok(topicDto.definition());
    }

    @GetMapping("/{title}/description")
    public ResponseEntity<String> getDescription(@PathVariable String title){
        TopicDto topicDto = topicService.findTopicByTitle(title);
        return ResponseEntity.ok(topicDto.description());
    }
}
