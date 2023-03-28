package com.operation.Online.Library.Circulation.System;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({ "Controllers", "Services", "Security", "Responses", "Requests", "NetworkAvailability" })
@EntityScan("Entities")
@EnableJpaRepositories("Repositories")
public class OnlineLibraryCirculationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineLibraryCirculationSystemApplication.class, args);
	}

	//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
}
