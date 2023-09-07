package com.sam.apihelpfulprofessor.dto;

import lombok.*;

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
    List<TopicExampleDto> examples;
}