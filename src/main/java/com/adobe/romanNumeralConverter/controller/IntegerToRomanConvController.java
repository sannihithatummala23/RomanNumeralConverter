package com.adobe.romanNumeralConverter.controller;

import com.adobe.romanNumeralConverter.exception.InvalidInputException;
import com.adobe.romanNumeralConverter.model.RomanNumeral;
import com.adobe.romanNumeralConverter.service.IntegerToRomanConverterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/romannumeral")
public class IntegerToRomanConvController {

    private final IntegerToRomanConverterService integerToRomanConverterService;


    public IntegerToRomanConvController(IntegerToRomanConverterService integerToRomanConverterService) {
        this.integerToRomanConverterService = integerToRomanConverterService;
    }

    /**
     * Services GET requests for converting Integer to Roman Numeral
     *
     * @param input
     * @return JSON output with input and Roman numeral
     * @throws InvalidInputException
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RomanNumeral> getConvertedRomanVal(@RequestParam("query") Integer input)
            throws InvalidInputException {
        log.info("::::Entered getConvertedRomanVal method in IntegerToRomanConvController class, input is :::: " + input);
        return ResponseEntity.status(HttpStatus.OK).body(integerToRomanConverterService.convertIntegerToRoman(input));
    }
}
