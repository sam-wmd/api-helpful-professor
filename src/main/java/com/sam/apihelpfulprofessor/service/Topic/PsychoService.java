package com.sam.apihelpfulprofessor.service.Topic;

import com.sam.apihelpfulprofessor.model.Category;
import com.sam.apihelpfulprofessor.repository.TopicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PsychoService extends TopicService {

    @Autowired
    public PsychoService(TopicRepository topicRepository){
        super(topicRepository);
        setCategory(Category.Psychology);
    }

}
