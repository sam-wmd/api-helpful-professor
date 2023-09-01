package com.sam.apihelpfulprofessor;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories
public class ApiHelpfulProfessorApplication  implements Runnable{


	public static void main(String[] args) {
		SpringApplication.run(ApiHelpfulProfessorApplication.class, args);
	}

	@Override
	public void run() {
		Dotenv dotenv = Dotenv.load();
	}
}
