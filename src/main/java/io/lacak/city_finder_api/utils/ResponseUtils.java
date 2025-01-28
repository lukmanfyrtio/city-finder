package io.lacak.city_finder_api.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.lacak.city_finder_api.dto.ResponseDTO;

public class ResponseUtils {
	// Utility method to create a success response
	public static <T> ResponseEntity<ResponseDTO<T>> createSuccessResponse(T data, String message) {
		ResponseDTO<T> response = new ResponseDTO<>("success", message, data);
		return ResponseEntity.ok(response);
	}

	// Utility method to create an error response
	public static <T> ResponseEntity<ResponseDTO<T>> createErrorResponse(String message, T data, HttpStatus status) {
		ResponseDTO<T> response = new ResponseDTO<>("error", message, data);
		return ResponseEntity.status(status).body(response);
	}
}
