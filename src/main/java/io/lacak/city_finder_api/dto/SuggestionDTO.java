package io.lacak.city_finder_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuggestionDTO {
	@Schema(description = "Name of the city", example = "Toronto")
	private String name;

	@Schema(description = "Latitude of the city", example = "43.70011")
	private double latitude;

	@Schema(description = "Longitude of the city", example = "-79.4163")
	private double longitude;

	@Schema(description = "Relevance score of the suggestion (0 to 1)", example = "0.87")
	private double score;
}
