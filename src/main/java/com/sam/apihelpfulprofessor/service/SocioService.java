package com.sam.apihelpfulprofessor.service;

import com.sam.apihelpfulprofessor.dto.TopicDto;
import com.sam.apihelpfulprofessor.mapper.ExampleMapper;
import com.sam.apihelpfulprofessor.mapper.TopicMapper;
import com.sam.apihelpfulprofessor.model.Category;
import com.sam.apihelpfulprofessor.model.Topic;
import com.sam.apihelpfulprofessor.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.ValidationException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class SocioService extends TopicService{

    @Autowired
    public SocioService(TopicRepository topicRepository) {
        super(topicRepository);
        this.setCategory(Category.Sociology);
    }



}
