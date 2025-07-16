package com.feelory.feelory_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FeeloryBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeeloryBackendApplication.class, args);
	}

}
