package com.ssafy.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AmtProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmtProjectApplication.class, args);
	}

}
