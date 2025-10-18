package com.ex.simplecrudrest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Pet Management API",
				version = "1.0",
				description = "REST API for managing pet information"
		)
)
public class SimpleCrudRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleCrudRestApplication.class, args);
	}
}
