package com.sam.apihelpfulprofessor.dto;

import com.sam.apihelpfulprofessor.model.TopicExample;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TopicDto
{
    String title;
    String definition;
    String description;
    String category;
    List<ExampleDto> examples;
}