package com.sam.apihelpfulprofessor.mapper;

import com.sam.apihelpfulprofessor.dto.TopicExampleDto;
import com.sam.apihelpfulprofessor.model.TopicExample;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ExampleMapper {

    ExampleMapper INSTANCE = Mappers.getMapper(ExampleMapper.class);

    @Mapping(source="name", target="name")
    @Mapping(source="description", target="description")
    TopicExample toEntity(TopicExampleDto dto);

    @Mapping(source="name", target="name")
    @Mapping(source="description", target="description")
    TopicExampleDto toDto(TopicExample example);

    List<TopicExampleDto> toDtoList(List<TopicExample> examples);
    List<TopicExample> toEntityList(List<TopicExampleDto> examples);
}
