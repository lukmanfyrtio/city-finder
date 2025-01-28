package io.lacak.city_finder_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CityFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(CityFinderApplication.class, args);
	}

}
