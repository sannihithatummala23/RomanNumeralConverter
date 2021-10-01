package com.adobe.romanNumeralConverter.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.adobe.romanNumeralConverter.common.RomanNumeralConstants;
import com.adobe.romanNumeralConverter.exception.InvalidInputException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice("com.adobe.romanNumeralConverter.controller")
public class RomanNumeralConverterExceptionHandler {

	/**
	 * Handles exception when input is not in range 1-3999
	 * @param exception
	 */
	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<String> handleInvalidInputException(InvalidInputException exception) {
		return new ResponseEntity<String>(exception.getErrorMessage(), HttpStatus.BAD_REQUEST);

	}

	/**
	 * Handles exception when no query parameter given in URI, string type inputs or for other invalid inputs
	 * @param exception
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGenericException(Exception exception) {
		log.error("::::Error Message::::" + exception.getMessage());
		return new ResponseEntity<String>(RomanNumeralConstants.USER_INPUT_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);

	}
}
