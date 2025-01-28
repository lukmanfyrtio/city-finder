package io.lacak.city_finder_api.utils;

public class JaroWinklerSimilarity {

	public static double calculateJaroWinkler(String s1, String s2) {

		s1 = s1.toLowerCase();
		s2 = s2.toLowerCase();

		if (s1 == null || s2 == null) {
			throw new IllegalArgumentException("Strings must not be null");
		}

		int s1Length = s1.length();
		int s2Length = s2.length();

		if (s1Length == 0 && s2Length == 0) {
			return 1.0;
		}

		int matchDistance = Math.max(s1Length, s2Length) / 2 - 1;

		boolean[] s1Matches = new boolean[s1Length];
		boolean[] s2Matches = new boolean[s2Length];

		int matches = 0;
		int transpositions = 0;

		// Find matches
		for (int i = 0; i < s1Length; i++) {
			int start = Math.max(0, i - matchDistance);
			int end = Math.min(i + matchDistance + 1, s2Length);

			for (int j = start; j < end; j++) {
				if (s2Matches[j])
					continue;
				if (s1.charAt(i) != s2.charAt(j))
					continue;
				s1Matches[i] = true;
				s2Matches[j] = true;
				matches++;
				break;
			}
		}

		if (matches == 0) {
			return 0.0;
		}

		// Count transpositions
		int s2MatchIndex = 0;
		for (int i = 0; i < s1Length; i++) {
			if (!s1Matches[i])
				continue;
			while (!s2Matches[s2MatchIndex]) {
				s2MatchIndex++;
			}
			if (s1.charAt(i) != s2.charAt(s2MatchIndex)) {
				transpositions++;
			}
			s2MatchIndex++;
		}

		transpositions /= 2;

		// Jaro similarity
		double jaro = ((double) matches / s1Length + (double) matches / s2Length
				+ (double) (matches - transpositions) / matches) / 3.0;

		// Jaro-Winkler adjustment
		int prefixLength = 0;
		for (int i = 0; i < Math.min(s1Length, s2Length); i++) {
			if (s1.charAt(i) == s2.charAt(i)) {
				prefixLength++;
			} else {
				break;
			}
		}

		prefixLength = Math.min(4, prefixLength); // Max prefix length of 4
		double prefixScale = 0.1; // Default scaling factor for Winkler adjustment

		double result = jaro + prefixLength * prefixScale * (1 - jaro);
		return Math.round(result * 100.0) / 100.0;
	}
}
