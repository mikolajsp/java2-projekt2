package pl.pw.mini.Schoolify.modules;

import java.util.List;

public class SearchResponseWrapper {
	private List<School> schoolList;
	private Double x_center;
	private Double y_center;
	private Double minLon;
	private Double maxLon;
	private Double minLat;
	private Double maxLat;

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
	public void setY_center(Double y_center) {
		this.y_center = y_center;
	}
	
	
}
