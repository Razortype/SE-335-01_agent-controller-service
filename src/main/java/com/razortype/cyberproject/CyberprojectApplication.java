package com.razortype.cyberproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CyberprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CyberprojectApplication.class, args);
	}

}
