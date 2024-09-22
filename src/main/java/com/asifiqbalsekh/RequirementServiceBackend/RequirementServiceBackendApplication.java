package com.asifiqbalsekh.RequirementServiceBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@EnableCaching//for implementing cache service
@SpringBootApplication
public class RequirementServiceBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(RequirementServiceBackendApplication.class, args);
	}

}
