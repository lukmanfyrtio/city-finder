package io.lacak.city_finder_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.lacak.city_finder_api.dto.ResponseDTO;
import io.lacak.city_finder_api.dto.SuggestionDTO;
import io.lacak.city_finder_api.service.GeonameService;
import io.lacak.city_finder_api.utils.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "City Controller", description = "API for city suggestions")
public class CityController {

	private final GeonameService geonameService;

	@Operation(summary = "Get city suggestions", description = "Returns a list of city suggestions based on the query and optional coordinates.", parameters = {
			@Parameter(name = "q", description = "Search query for city names", required = true, example = "London"),
			@Parameter(name = "latitude", description = "Optional latitude for geographical filtering", required = false, example = "43.70011"),
			@Parameter(name = "longitude", description = "Optional longitude for geographical filtering", required = false, example = "-79.4163") })
	@GetMapping("/suggestions")
	public ResponseEntity<ResponseDTO<List<SuggestionDTO>>> getSuggestions(@RequestParam String q, @RequestParam(required = false) Double latitude,
			@RequestParam(required = false) Double longitude) {
		return ResponseUtils.createSuccessResponse(geonameService.getSuggestions(q, latitude, longitude),
				"City suggestions fetched successfully");
	}

}
