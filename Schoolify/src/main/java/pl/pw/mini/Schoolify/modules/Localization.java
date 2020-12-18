package pl.pw.mini.Schoolify.modules;

import javax.persistence.Embeddable;

@Embeddable
public class Localization {
	
	
	private String voivodeship;
	private String county;
	private String community;
	private String street;
	private String houseNumber;
	private String postalCode;
	private Double lon;
	private Double lat;
	private String town;
	
	
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}


	public Localization(String voivodeship, String county, String community, String street, String houseNumber,
			String postalCode, Double lon, Double lat, String town) {
		this.voivodeship = voivodeship;
		this.county = county;
		this.community = community;
		this.street = street;
		this.houseNumber = houseNumber;
		this.postalCode = postalCode;
		this.lon = lon;
		this.lat = lat;
		this.town = town;
	}
	@Override
	public String toString() {
		return "Localization [voivodeship=" + voivodeship + ", county=" + county + ", community=" + community
				+ ", street=" + street + ", houseNumber=" + houseNumber + ", postalCode=" + postalCode + ", lon=" + lon
				+ ", lat=" + lat + ", town=" + town + "]";
	}
	public Double getLon() {
		return lon;
	}
	public void setLon(Double lon) {
		this.lon = lon;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Localization() {}
	public String getVoivodeship() {
		return voivodeship;
	}
	public void setVoivodeship(String voivodeship) {
		this.voivodeship = voivodeship;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getHouseNumber() {
		return houseNumber;
	}
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}


}
