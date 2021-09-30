package com.adobe.romanNumeralConverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ComponentScan(basePackages = "com.adobe.romanNumeralConverter")
@Configuration
public class DemoprojectApplication {

	public static void main(String[] args) {

		SpringApplication.run(DemoprojectApplication.class, args);

	}

}
