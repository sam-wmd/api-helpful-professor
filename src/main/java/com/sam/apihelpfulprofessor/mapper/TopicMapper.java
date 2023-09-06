package com.sam.apihelpfulprofessor.mapper;

import com.sam.apihelpfulprofessor.dto.TopicDto;
import com.sam.apihelpfulprofessor.model.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {ExampleMapper.class})
public interface TopicMapper {

    TopicMapper INSTANCE = Mappers.getMapper(TopicMapper.class);

    TopicDto toDto(Topic topic);

    Topic toEntity(TopicDto dto);


    List<Topic> topics(List<TopicDto> topicDtos);
    List<TopicDto> topicDtos(List<Topic> topics);
}
