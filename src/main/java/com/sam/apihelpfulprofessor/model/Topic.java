package com.sam.apihelpfulprofessor.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@With
@Node
@JsonIgnoreProperties(ignoreUnknown = true)
public class Topic {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String definition;
    private String description;

    private Category category;
    private List<TopicExample> examples;


}
