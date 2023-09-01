package com.sam.apihelpfulprofessor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TopicExample {

    @Id @GeneratedValue
    private Long id;
    private String name;
    private String description;
}
