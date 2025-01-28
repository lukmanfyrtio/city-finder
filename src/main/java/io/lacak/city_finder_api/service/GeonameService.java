package io.lacak.city_finder_api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import io.lacak.city_finder_api.dto.Geoname;
import io.lacak.city_finder_api.dto.SuggestionDTO;
import io.lacak.city_finder_api.utils.Haversine;
import io.lacak.city_finder_api.utils.JaroWinklerSimilarity;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GeonameService {
	private static final List<Geoname> geonames = new CopyOnWriteArrayList<>();
	private static final double JARO_WINKLER_WEIGHT = 0.5;
	private static final double HAVERSINE_WEIGHT = 0.5;

	@PostConstruct
	public static void loadGeonamesData() throws IOException {
		Instant start = Instant.now();
		log.info("Loading geonames data from the TSV file...");

		ClassPathResource resource = new ClassPathResource("./datasource/cities_canada-usa.tsv");
		List<CompletableFuture<Void>> futures = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
			String line;

			// Skip the first line (header)
			br.readLine();

			while ((line = br.readLine()) != null) {
				log.debug("Processing line: {}", line);
				CompletableFuture<Void> future = processLineAsync(line);
				futures.add(future);
			}
		}

		// Wait for all tasks to complete
		CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

		Instant end = Instant.now();
		long duration = java.time.Duration.between(start, end).toMillis();

		log.info("Geonames data loading completed in {} milliseconds. Loaded {} records.", duration, geonames.size());
	}

	@Async
	public static CompletableFuture<Void> processLineAsync(String line) {
		return CompletableFuture.runAsync(() -> {
			try {

				// Parse the line into geoname data
				String[] fields = line.split("\\t");
				if (fields.length >= 18) {
					Geoname geoname = new Geoname();

					geoname.setGeonameid(Integer.parseInt(fields[0]));
					geoname.setName(fields[1].isEmpty() ? "N/A" : fields[1]);
					geoname.setAsciiname(fields[2].isEmpty() ? "N/A" : fields[2]);
					geoname.setAlternatenames(fields[3].isEmpty() ? "N/A" : fields[3]);
					geoname.setLatitude(fields[4].isEmpty() ? 0.0 : Double.parseDouble(fields[4]));
					geoname.setLongitude(fields[5].isEmpty() ? 0.0 : Double.parseDouble(fields[5]));
					geoname.setFeatureClass(fields[6].isEmpty() ? "N/A" : fields[6]);
					geoname.setFeatureCode(fields[7].isEmpty() ? "N/A" : fields[7]);
					geoname.setCountryCode(fields[8].isEmpty() ? "N/A" : fields[8]);
					geoname.setCc2(fields[9].isEmpty() ? "N/A" : fields[9]);
					geoname.setAdmin1Code(fields[10].isEmpty() ? "N/A" : fields[10]);
					geoname.setAdmin2Code(fields[11].isEmpty() ? "N/A" : fields[11]);
					geoname.setAdmin3Code(fields[12].isEmpty() ? "N/A" : fields[12]);
					geoname.setAdmin4Code(fields[13].isEmpty() ? "N/A" : fields[13]);
					geoname.setPopulation(fields[14].isEmpty() ? 0 : Long.parseLong(fields[14]));
					geoname.setElevation(fields[15].isEmpty() ? 0 : Integer.parseInt(fields[15]));
					geoname.setDem(fields[16].isEmpty() ? 0 : Integer.parseInt(fields[16]));
					geoname.setTimezone(fields[17].isEmpty() ? "N/A" : fields[17]);
					geoname.setModificationDate(fields[18].isEmpty() ? "N/A" : fields[18]);

					// Add to geonames list
					geonames.add(geoname);

					// Log the successful processing of the line
					log.debug("Geoname processed: {}", geoname);
				}
			} catch (Exception e) {
				// Log any exceptions encountered during the processing
				log.error("Error processing line: {}", line, e);
			}
		});
	}

	// Static getter for the geonames list (if you need to retrieve the loaded data)
	public static List<Geoname> getGeonames() {
		return geonames;
	}

	public static double combineScores(double score1, double score2, double weight1, double weight2) {
		// Ensure weights sum up to 1 (or scale as needed)
		double totalWeight = weight1 + weight2;
		double normalizedWeight1 = weight1 / totalWeight;
		double normalizedWeight2 = weight2 / totalWeight;

		// Calculate combined score
		return (score1 * normalizedWeight1) + (score2 * normalizedWeight2);
	}

	public List<SuggestionDTO> getSuggestions(String q, Double latitude, Double longitude) {
		return geonames.parallelStream().filter(city -> city.getName().toLowerCase().contains(q.toLowerCase()))
				.map(city -> {
					double score = 0;
					score = JaroWinklerSimilarity.calculateJaroWinkler(city.getName(), q);
					if (latitude != null && longitude != null) {
						double haversineScore = Haversine.haversineScore(latitude, longitude, city.getLatitude(),
								city.getLongitude());
						// Combine scores with weights
						score = combineScores(score, haversineScore, JARO_WINKLER_WEIGHT, HAVERSINE_WEIGHT);
					}
					return SuggestionDTO
							.builder().latitude(city.getLatitude()).longitude(city.getLongitude()).name(String
									.format("%s, %s, %s", city.getName(), city.getAdmin1Code(), city.getCountryCode()))
							.score(Math.round(score * 100.0) / 100.0).build();
				}).sorted(Comparator.comparingDouble(SuggestionDTO::getScore).reversed()).collect(Collectors.toList());
	}
}
