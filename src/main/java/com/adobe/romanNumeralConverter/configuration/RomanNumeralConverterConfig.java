package com.adobe.romanNumeralConverter.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/*
This binds the properties defined in the application.properties file to the instance variables.
*/

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
