package com.adobe.romanNumeralConverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@ComponentScan(basePackages = "com.adobe.romanNumeralConverter")
@Configuration
public class RomanNumeralConverterProject {

    public static void main(String[] args) {

        SpringApplication.run(RomanNumeralConverterProject.class, args);

    }

}
