package com.matchmaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.matchmaker.repository")
public class MatchmakerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatchmakerApplication.class, args);
	}
}
