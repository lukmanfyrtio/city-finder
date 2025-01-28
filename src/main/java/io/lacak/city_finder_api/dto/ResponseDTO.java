package io.lacak.city_finder_api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResponseDTO<T> {
	@JsonProperty("timestamp")
	private final long timestamp = System.currentTimeMillis();
	private String status;
	private String message;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;
}