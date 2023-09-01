package com.sam.apihelpfulprofessor.controller;

import com.sam.apihelpfulprofessor.dto.TopicDto;
import com.sam.apihelpfulprofessor.mapper.TopicMapper;
import com.sam.apihelpfulprofessor.repository.TopicRepository;
import com.sam.apihelpfulprofessor.service.PsychoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/psychology")
public class PsychoController extends TopicController {

    @Autowired
    public PsychoController(PsychoService psychoService){
        super(psychoService);
    }

}
