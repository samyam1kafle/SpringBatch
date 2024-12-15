package com.samyam.etl.demo.DataExtractionService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DataExtractionServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(DataExtractionServiceApplication.class, args);
	}

}
