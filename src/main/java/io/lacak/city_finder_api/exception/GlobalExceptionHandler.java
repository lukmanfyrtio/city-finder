package io.lacak.city_finder_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.lacak.city_finder_api.dto.ResponseDTO;
import io.lacak.city_finder_api.utils.ResponseUtils;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseDTO<String>> handleException(Exception ex) {
		// Return a generic error response
		return ResponseUtils.createErrorResponse("An unexpected error occurred: " + ex.getMessage(), null,
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
