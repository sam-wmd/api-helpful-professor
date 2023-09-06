package com.sam.apihelpfulprofessor.mapper;

import com.sam.apihelpfulprofessor.dto.ExampleDto;
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
    TopicExample toEntity(ExampleDto dto);

    @Mapping(source="name", target="name")
    @Mapping(source="description", target="description")
    ExampleDto toDto(TopicExample example);

    List<ExampleDto> toDtoList(List<TopicExample> examples);
    List<TopicExample> toEntityList(List<ExampleDto> examples);
}
