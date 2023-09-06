package com.sam.apihelpfulprofessor.controller;

import com.sam.apihelpfulprofessor.dto.SearchResponseDto;
import com.sam.apihelpfulprofessor.service.Langchain.LangChainService;
import com.sam.apihelpfulprofessor.service.Milvus.MilvusService;
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

    final MilvusService milvusService;

    @GetMapping("/ping")
    public ResponseEntity<?> ping(){
        return ResponseEntity.ok("Pong");
    }

    @GetMapping("/search-milvus")
    public ResponseEntity<SearchResponseDto> semanticSearch(@RequestBody Map<String,String> request){

        String question = request.get("query");
        return ResponseEntity.ok(milvusService.search(question));
    }

    @GetMapping("/search-langchain")
    public ResponseEntity<String> chainSearch(@RequestBody Map<String,String> request){

        String question = request.get("query");
        return ResponseEntity.ok(LangChainService.CHAIN.execute(question));
    }
}
