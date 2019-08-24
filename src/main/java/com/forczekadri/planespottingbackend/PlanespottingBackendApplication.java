package com.forczekadri.planespottingbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PlanespottingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlanespottingBackendApplication.class, args);
	}

}
