package com.adobe.romanNumeralConverter.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties("romannumerals")
public class RomanNumeralConverterConfig {
	private String[] units;
	private String[] tens;
	private String[] hundreds;
	private String[] thousands;
}
