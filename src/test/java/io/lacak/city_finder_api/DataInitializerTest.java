package io.lacak.city_finder_api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import io.lacak.city_finder_api.dto.SuggestionDTO;
import io.lacak.city_finder_api.service.GeonameService;

public class DataInitializerTest {

	@InjectMocks
	private GeonameService geonameService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);	
		try {
			if (geonameService.getGeonames().size() == 0)
				geonameService.loadGeonamesData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void testLoadGeonamesData_IOException() {
		assertEquals(7237, geonameService.getGeonames().size(), "Data loaded should 7237");
	}

	@Test
	void testGetSuggestions() {
		List<SuggestionDTO> data = geonameService.getSuggestions("Austintown", null, null);
		assertEquals(1, data.get(0).getScore(), "Score must be 1.0");
	}

}