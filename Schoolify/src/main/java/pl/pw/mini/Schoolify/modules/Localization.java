package pl.pw.mini.Schoolify.modules;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Localization implements Serializable {

	private static final long serialVersionUID = 1L;
	private String voivodeship;
	private String county;
	private String community;
	private String street;
	private String houseNumber;
	private String postalCode;
	
	public Localization(String voivodeship, String county, String community, String street, String houseNumber,
			String postalCode) {
		super();
		this.voivodeship = voivodeship;
		this.county = county;
		this.community = community;
		this.street = street;
		this.houseNumber = houseNumber;
		this.postalCode = postalCode;
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
	@Override
	public String toString() {
		return "Localization [voivodeship=" + voivodeship + ", county=" + county + ", community=" + community
				+ ", street=" + street + ", houseNumber=" + houseNumber + ", postalCode=" + postalCode + "]";
	}

}
