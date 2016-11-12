package com.userj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EntityScan(basePackageClasses = { ComUserjApplication.class, Jsr310JpaConverters.class })
@SpringBootApplication
@EnableJpaAuditing
public class ComUserjApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComUserjApplication.class, args);
	}
	
}
