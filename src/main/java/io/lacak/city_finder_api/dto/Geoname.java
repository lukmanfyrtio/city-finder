package io.lacak.city_finder_api.dto;

import lombok.Data;

@Data
public class Geoname {
	private int geonameid;
	private String name;
	private String asciiname;
	private String alternatenames;
	private double latitude;
	private double longitude;
	private String featureClass;
	private String featureCode;
	private String countryCode;
	private String cc2;
	private String admin1Code;
	private String admin2Code;
	private String admin3Code;
	private String admin4Code;
	private long population;
	private int elevation;
	private int dem;
	private String timezone;
	private String modificationDate;
}
