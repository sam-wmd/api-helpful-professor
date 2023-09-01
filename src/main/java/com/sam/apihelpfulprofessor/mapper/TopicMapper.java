package com.sam.apihelpfulprofessor.mapper;

import com.sam.apihelpfulprofessor.dto.TopicDto;
import com.sam.apihelpfulprofessor.model.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(uses = {ExampleMapper.class})
public interface TopicMapper {

    TopicMapper INSTANCE = Mappers.getMapper(TopicMapper.class);

    TopicDto toDto(Topic topic);

    Topic toEntity(TopicDto dto);
}
