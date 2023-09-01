package com.sam.apihelpfulprofessor.dto;

import com.sam.apihelpfulprofessor.model.TopicExample;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

public record TopicDto (
    String title,
    String definition,
    String description,
    String category,
    List<ExampleDto> examples
)
{ }