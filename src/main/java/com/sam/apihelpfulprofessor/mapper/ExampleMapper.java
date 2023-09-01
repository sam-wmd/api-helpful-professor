package com.sam.apihelpfulprofessor.mapper;

import com.sam.apihelpfulprofessor.dto.ExampleDto;
import com.sam.apihelpfulprofessor.model.TopicExample;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface ExampleMapper {

    ExampleMapper INSTANCE = Mappers.getMapper(ExampleMapper.class);

    TopicExample toEntity(ExampleDto dto);
    ExampleDto toDto(TopicExample example);

    List<ExampleDto> toDtoList(List<TopicExample> examples);
    List<TopicExample> toEntityList(List<ExampleDto> examples);
}
