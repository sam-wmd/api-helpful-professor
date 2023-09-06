package com.sam.apihelpfulprofessor.service.Topic;

import com.sam.apihelpfulprofessor.model.Category;
import com.sam.apihelpfulprofessor.repository.TopicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class SocioService extends TopicService{

    @Autowired
    public SocioService(TopicRepository topicRepository) {
        super(topicRepository);
        this.setCategory(Category.Sociology);
    }

}
