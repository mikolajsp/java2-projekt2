package pl.pw.mini.Schoolify.modules;

import java.util.List;

public class SearchResponseWrapper {
	private List<School> schoolList;
	private List<School> mostPopular;
	private Double x_center;
	private Double y_center;
	private Double minLon;
	private Double maxLon;
	private Double minLat;
	private Double maxLat;
	private Double originX;
	private Double originY;
	private Double std;
	private String address;

	public String getAddress() {
		return address;
	}
	public Double getOriginX() {
		return originX;
	}
	public void setOriginX(Double originX) {
		this.originX = originX;
	}
	public Double getOriginY() {
		return originY;
	}
	public void setOriginY(Double originY) {
		this.originY = originY;
	}
	public Double getMinLon() {
		return minLon;
	}
	public void setMinLon(Double minLon) {
		this.minLon = minLon;
	}
	public Double getMaxLon() {
		return maxLon;
	}
	public void setMaxLon(Double maxLon) {
		this.maxLon = maxLon;
	}
	public Double getMinLat() {
		return minLat;
	}
	public void setMinLat(Double minLat) {
		this.minLat = minLat;
	}
	public Double getMaxLat() {
		return maxLat;
	}
	public void setMaxLat(Double maxLat) {
		this.maxLat = maxLat;
	}
	public List<School> getSchoolList() {
		return schoolList;
	}
	public void setSchoolList(List<School> schoolList) {
		this.schoolList = schoolList;
	}
	public Double getX_center() {
		return x_center;
	}
	public void setX_center(Double x_center) {
		this.x_center = x_center;
	}
	public Double getY_center() {
		return y_center;
	}
	public void setAddress(String adress) {
		this.address = adress;
	}
	public void setY_center(Double y_center) {
		this.y_center = y_center;
	}
	public List<School> getMostPopular() {
		return mostPopular;
	}
	public void setMostPopular(List<School> mostPopular) {
		this.mostPopular = mostPopular;
	}
	public Double getStd() {
		return std;
	}
	public void setStd(Double std) {
		this.std = std;
	}
	
	
}
