package pl.pw.mini.Schoolify.modules;

import java.util.ArrayList;
import java.util.List;

public class MultipleSchoolResponseWrapper {
	List<SingleSchoolResponseWrapper> schools = new ArrayList<>();

	public List<SingleSchoolResponseWrapper> getSchools() {
		return schools;
	}

	public void setSchools(List<SingleSchoolResponseWrapper> schools) {
		this.schools = schools;
	}
	public void addSchool(SingleSchoolResponseWrapper s) {
		this.schools.add(s);
	}

	
}