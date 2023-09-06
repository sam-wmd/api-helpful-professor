package com.sam.apihelpfulprofessor.service.Topic;

import com.sam.apihelpfulprofessor.dto.ExampleDto;
import com.sam.apihelpfulprofessor.dto.TopicDto;
import com.sam.apihelpfulprofessor.mapper.ExampleMapper;
import com.sam.apihelpfulprofessor.mapper.TopicMapper;
import com.sam.apihelpfulprofessor.model.Category;
import com.sam.apihelpfulprofessor.model.Topic;
import com.sam.apihelpfulprofessor.repository.TopicRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class TopicService {

    final TopicRepository topicRepository;
    ExampleMapper exampleMapper = ExampleMapper.INSTANCE;
    TopicMapper topicMapper = TopicMapper.INSTANCE;
    Category category;
    Example<Topic> example;


    @Autowired
    TopicService(TopicRepository topicRepository){
        this.topicRepository = topicRepository;
    }

    public void setCategory(Category category) {
        this.category = category;
        this.example = Example.of(new Topic().withCategory(category));
    }

    public TopicDto findTopicWithExamples(String title){
        TopicDto topic = topicMapper.toDto(topicRepository.findByTitle(title));
        List<ExampleDto> examples = topicRepository.findTopicExamples(title);
        topic.setExamples(examples);
        return topic;

    }
    public TopicDto findTopicByTitle(String title){
        title = StringUtils.capitalize(title);
        if (example instanceof Example<Topic>){
            Topic topic = topicRepository.findOne(Example.of(example.getProbe().withTitle(title))).orElse(null);
            return topicMapper.toDto(topic);
        }
        return topicMapper.toDto(topicRepository.findByTitle(title));
    }

    public List<TopicDto> getAllTopics(){
        List<Topic> topics = IterableUtils.toList(topicRepository.findAll(example));
        return topics.stream().map(topicMapper::toDto).collect(Collectors.toList());
    }

    public void update(TopicDto topicDto){
        Topic topic = topicRepository.findByTitle(topicDto.getTitle());
        topic.setDefinition(topicDto.getDefinition());
        topic.setDescription(topicDto.getDescription());
        topic.setExamples(topicDto.getExamples().stream().map(exampleMapper::toEntity).toList());
        topicRepository.save(topic);
    }


    public boolean delete(String title){
        Topic topic = topicRepository.findByTitle(StringUtils.capitalize(title));
        if (topic == null){
            return false;
        }
        topicRepository.deleteById(topic.getId());
        return true;
    }

    public Topic create(TopicDto topicDto){
        return topicRepository.save(topicMapper.toEntity(topicDto));
    }

}
