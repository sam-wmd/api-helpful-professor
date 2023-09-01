package com.sam.apihelpfulprofessor.service;

import com.sam.apihelpfulprofessor.mapper.ExampleMapper;
import com.sam.apihelpfulprofessor.mapper.TopicMapper;
import com.sam.apihelpfulprofessor.model.Category;
import com.sam.apihelpfulprofessor.model.Topic;
import com.sam.apihelpfulprofessor.model.TopicExample;
import com.sam.apihelpfulprofessor.repository.TopicRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PsychoService extends TopicService {

    @Autowired
    public PsychoService(TopicRepository topicRepository){
        super(topicRepository);
        setCategory(Category.Psychology);
    }

}
