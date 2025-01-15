package com.umc.meetpick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MeetpickApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetpickApplication.class, args);
	}

}
