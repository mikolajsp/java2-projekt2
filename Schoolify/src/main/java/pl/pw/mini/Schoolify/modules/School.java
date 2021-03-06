package pl.pw.mini.Schoolify.modules;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

@Entity
@Table(name = "schools")
public class School {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String type;
	private String name;
	private Integer popularity;
	@Embedded
	@JsonUnwrapped
	private Contact contact;

	@OneToMany(mappedBy = "schoolId")
	private List<Comment> comments;

	@OneToOne
	@JoinColumn(name = "id")
	private Assesment assesment;

	@Embedded
	@JsonUnwrapped
	private Localization localization;

	public Assesment getAssesment() {
		return assesment;
	}

	public void setAssesment(Assesment assesment) {
		this.assesment = assesment;
	}

	private Integer students;
	private Integer branches;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStudents() {
		return students;
	}

	public void setStudents(int students) {
		this.students = students;
	}

	public Integer getBranches() {
		return branches;
	}

	public void setBranches(int branches) {
		this.branches = branches;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public Localization getLocalization() {
		return localization;
	}

	public void setLocalization(Localization localization) {
		this.localization = localization;
	}

	// haversine formula
	public Double calculateDistance(double lon, double lat) {
		int R = 6370000; // earth radius
		double pom = Math.PI / 180;
		double ph1 = lat * pom;
		double ph2 = localization.getLat() * pom;
		double deltaphi = (localization.getLat() - lat) * pom;
		double deltalambda = (localization.getLon() - lon) * pom;
		double a = Math.sin(deltaphi / 2) * Math.sin(deltaphi / 2)
				+ Math.cos(ph1) * Math.cos(ph2) * Math.sin(deltalambda / 2) * Math.sin(deltalambda / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return R * c;
	}

	@Override
	public String toString() {
		return "School [id=" + id + ", type=" + type + ", name=" + name + ", students=" + students + ", branches="
				+ branches + "]";
	}

	public Integer getPopularity() {
		return popularity;
	}

	public void setPopularity(Integer popularity) {
		this.popularity = popularity;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

}
