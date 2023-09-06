package com.sam.apihelpfulprofessor.controller;

import com.sam.apihelpfulprofessor.service.Topic.PsychoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/psychology")
public class PsychoController extends TopicController {

    @Autowired
    public PsychoController(PsychoService psychoService){
        super(psychoService);
    }

}
