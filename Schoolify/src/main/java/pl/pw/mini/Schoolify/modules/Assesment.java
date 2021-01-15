package pl.pw.mini.Schoolify.modules;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="assesment")
public class Assesment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Double educational;
	private Double friendliness;
	private Double intrests;
	private Double lowPrice;
	private Double commute;
	private Integer votes_number;
	
	@OneToOne
	@JoinColumn(name="id")
	private School school;
	
	
	
	public Assesment() {}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getEducational() {
		return educational;
	}
	public void setEducational(Double educational) {
		this.educational = educational;
	}
	public Double getFriendliness() {
		return friendliness;
	}
	public void setFriendliness(Double friendliness) {
		this.friendliness = friendliness;
	}
	public Double getIntrests() {
		return intrests;
	}
	public void setIntrests(Double intrests) {
		this.intrests = intrests;
	}
	public Double getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(Double lowPrice) {
		this.lowPrice = lowPrice;
	}
	public Double getCommute() {
		return commute;
	}
	public void setCommute(Double commute) {
		this.commute = commute;
	}
	public Integer getVotes_number() {
		return votes_number;
	}
	public void setVotes_number(Integer votes_number) {
		this.votes_number = votes_number;
	}

	
	
}
