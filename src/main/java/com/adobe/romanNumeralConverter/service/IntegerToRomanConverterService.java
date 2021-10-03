package com.adobe.romanNumeralConverter.service;

import com.adobe.romanNumeralConverter.common.RomanNumeralConstants;
import com.adobe.romanNumeralConverter.configuration.RomanNumeralConverterConfig;
import com.adobe.romanNumeralConverter.exception.InvalidInputException;
import com.adobe.romanNumeralConverter.model.RomanNumeral;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IntegerToRomanConverterService {

    private final RomanNumeralConverterConfig romanNumeralConverterConfig;

    public IntegerToRomanConverterService(RomanNumeralConverterConfig romanNumeralConverterConfig) {
        this.romanNumeralConverterConfig = romanNumeralConverterConfig;
    }


    /**
     * Converts Integer to Roman numeral
     *
     * @param input
     * @return RomanNumeral POJO with input and output as Strings
     * @throws InvalidInputException
     */
    public RomanNumeral convertIntegerToRoman(Integer input) throws InvalidInputException {

        log.info("::::Entered convertIntegerToRoman method inside IntegerToRomanConvService class::::");
        String result = "";

        if (input <= 0 || input > 3999) {
            log.error("::::Convertion is not supported for the integer greater than 3999 or less than 0::::" + input);
            throw new InvalidInputException(RomanNumeralConstants.USER_INPUT_ERROR_MESSAGE);
        }

        result = romanNumeralConverterConfig.getThousands()[input / 1000]
                + romanNumeralConverterConfig.getHundreds()[(input % 1000) / 100]
                + romanNumeralConverterConfig.getTens()[(input % 100) / 10]
                + romanNumeralConverterConfig.getUnits()[input % 10];
        log.info("::::Roman Numeral value is::::" + result);

        return RomanNumeral.builder().input(input.toString()).output(result).build();

    }

}
