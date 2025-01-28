package io.lacak.city_finder_api.utils;

public class Haversine {

	private static final int R = 6371; // Radius of the Earth in KM
	private static final int THRESHOLD_KM_SCORE = 1000;

	public static double haversineScore(double lat1, double lon1, double lat2, double lon2) {

		// Convert latitude and longitude from degrees to radians
		double lat1Rad = Math.toRadians(lat1);
		double lon1Rad = Math.toRadians(lon1);
		double lat2Rad = Math.toRadians(lat2);
		double lon2Rad = Math.toRadians(lon2);

		// Difference in coordinates
		double dLat = lat2Rad - lat1Rad;
		double dLon = lon2Rad - lon1Rad;

		// Haversine formula
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		// Distance in kilometers
		return normalizeScore(R * c, THRESHOLD_KM_SCORE);
	}

	public static double normalizeScore(double distance, double maxDistance) {
		return 1 - Math.min(distance, maxDistance) / maxDistance;
	}

}
