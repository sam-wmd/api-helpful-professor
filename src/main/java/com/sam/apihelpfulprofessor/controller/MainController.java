package com.sam.apihelpfulprofessor.controller;

import com.sam.apihelpfulprofessor.dto.TopicDto;
import com.sam.apihelpfulprofessor.service.LangChainService;
import dev.langchain4j.internal.Json;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.sam.apihelpfulprofessor.service.LangChainService.DATA_EXTRACTOR;


@RestController
@RequestMapping("/api")
public class MainController {


    @GetMapping("/ping")
    public ResponseEntity<?> ping(){
        return ResponseEntity.ok("Pong");
    }



    @GetMapping("/question")
    public ResponseEntity<?> semanticSearch(@RequestBody Map<String,String> request){

        String question = request.get("question");
        return ResponseEntity.ok(LangChainService.CHAIN.execute(question));

    }

    @GetMapping("/json")
    public ResponseEntity<?> getJson(@RequestBody Map<String,String> req){
        String text = req.get("text");
        List<Json> response = DATA_EXTRACTOR.getJson(text);

        return ResponseEntity.ok(response);
    }
}
