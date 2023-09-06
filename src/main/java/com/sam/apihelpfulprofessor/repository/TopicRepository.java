package com.sam.apihelpfulprofessor.repository;

import com.sam.apihelpfulprofessor.dto.ExampleDto;
import com.sam.apihelpfulprofessor.model.Topic;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends Neo4jRepository<Topic, Long>, QueryByExampleExecutor<Topic> {


    @Query("MATCH (t:Topic {title: $title}) return t")
    Topic findByTitle(String title);

    @Query("MATCH (ex:TopicExample)<-[:EXAMPLES]-(t:Topic {title:$title}) return ex.name as name, ex.description as description")
    List<ExampleDto> findTopicExamples(String title);
}
