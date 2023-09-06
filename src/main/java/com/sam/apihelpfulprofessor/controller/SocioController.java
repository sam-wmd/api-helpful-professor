package com.sam.apihelpfulprofessor.controller;

import com.sam.apihelpfulprofessor.service.Topic.SocioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/sociology")
@RequiredArgsConstructor
public class SocioController extends TopicController {


    @Autowired
    public SocioController(SocioService socioService){
        super(socioService);
    }
}
