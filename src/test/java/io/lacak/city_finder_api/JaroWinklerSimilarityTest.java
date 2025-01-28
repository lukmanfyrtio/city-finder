package io.lacak.city_finder_api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.lacak.city_finder_api.utils.JaroWinklerSimilarity;

public class JaroWinklerSimilarityTest {

	@Test
	void testCalculateJaroWinkler_EqualStrings() {
		String s1 = "hello";
		String s2 = "hello";
		double result = JaroWinklerSimilarity.calculateJaroWinkler(s1, s2);
		assertEquals(1.0, result, "The similarity should be 1.0 for identical strings");
	}

	@Test
	void testCalculateJaroWinkler_SimilarStrings() {
		String s1 = "hello";
		String s2 = "hallo";
		double result = JaroWinklerSimilarity.calculateJaroWinkler(s1, s2);
		assertTrue(result > 0.8, "The similarity should be high for similar strings");
	}
}
