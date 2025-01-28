package io.lacak.city_finder_api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.lacak.city_finder_api.utils.Haversine;

public class HaversineTest {
	@Test
	void testHaversineScore_CalculatesDistanceCorrectly() {
		double lat1 = 43.70011;
		double lon1 = -79.4163;

		double lat2 = 38.93345;
		double lon2 = -76.54941;

		double expectedScore = Haversine.haversineScore(lat1, lon1, lat2, lon2);

		assertTrue(expectedScore >= 0 && expectedScore <= 1, "Score should be between 0 and 1");
	}

	@Test
	void testNormalizeScore_CorrectlyNormalizesDistance() {
		double distance = 500;
		double threshold = 1000;

		double normalizedScore = Haversine.normalizeScore(distance, threshold);

		assertEquals(0.5, normalizedScore, "Score should be normalized correctly for distance 500km");
	}

	@Test
	void testNormalizeScore_WithZeroDistance() {
		double distance = 0;
		double threshold = 1000;

		double normalizedScore = Haversine.normalizeScore(distance, threshold);

		assertEquals(1, normalizedScore, "Score should be 1 for a distance of 0km");
	}
}
