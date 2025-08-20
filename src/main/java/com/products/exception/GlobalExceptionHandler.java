package com.products.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InvalidNameException.class)
	public ResponseEntity<String> handleInvalidNameException(InvalidNameException e1){
		return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidDescriptionException.class)
	public ResponseEntity<String> handleInvalidDescriptionException(InvalidDescriptionException e2){
		return new ResponseEntity<>(e2.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidPriceException.class)
	public ResponseEntity<String> handleInvalidPriceException(InvalidPriceException e3){
		return new ResponseEntity<>(e3.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ProductDoesNotExistException.class)
	public ResponseEntity<String> handleProductDoesNotExistException(ProductDoesNotExistException e4){
		return new ResponseEntity<>(e4.getMessage(), HttpStatus.NOT_FOUND);
	}
	
}
