package pl.pw.mini.Schoolify.modules;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="schools")
public class School {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String type;
	private String name;
	// adnotacja pozwalająca zagnieżdzać obiekty
	@Embedded 
	private Localization localization;
	@Embedded 
	private Contact contact;
	private int students;
	private int branches;
	
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
	public Localization getLocalization() {
		return localization;
	}
	public void setLocalization(Localization localization) {
		this.localization = localization;
	}
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public int getStudents() {
		return students;
	}

	public void setStudents(int students) {
		this.students = students;
	}
	public int getBranches() {
		return branches;
	}
	public void setBranches(int branches) {
		this.branches = branches;
	}
	
	
	@Override
	public String toString() {
		return "School [id=" + id + ", type=" + type + ", name=" + name + ", localization=" + localization
				+ ", contact=" + contact + ", students=" + students + ", branches=" + branches + "]";
	}
	
	
	
	
}
