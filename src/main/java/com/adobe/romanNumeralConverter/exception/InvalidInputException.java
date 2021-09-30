package com.adobe.romanNumeralConverter.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvalidInputException extends RuntimeException {

	private String errorMessage;

}
