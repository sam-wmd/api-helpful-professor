package com.sam.apihelpfulprofessor.controller;

import com.sam.apihelpfulprofessor.service.Langchain.LangChainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MainController {

    final LangChainService langChainService;

    @GetMapping("/ping")
    public ResponseEntity<?> ping(){
        return ResponseEntity.ok("Pong");
    }

    @GetMapping("/search-langchain")
    public ResponseEntity<String> chainSearch(@RequestBody Map<String,String> request){

        String question = request.get("query");
        return ResponseEntity.ok(LangChainService.CHAIN.execute(question));
    }
}
