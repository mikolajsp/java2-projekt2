package pl.pw.mini.Schoolify.modules;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name="comments")
public class Comment {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long schoolId;
	private String content;
	private Integer rate;
	private String user;
	private Integer upVotes;
	private Integer downVotes;
	private LocalDate date;
	
	@ManyToOne
	@JoinColumn
	private School school;
	
	public Comment() {
		
	}
	
	
	
	public Comment(Long id, Long schoolId, String content, Integer rate, String user, Integer upVotes, Integer downVotes,
			LocalDate date) {
		this.id = id;
		this.schoolId = schoolId;
		this.content = content;
		this.rate = rate;
		this.user = user;
		this.upVotes = upVotes;
		this.downVotes = downVotes;
		this.date = date;
	}



	@Override
	public String toString() {
		return "Comment [id=" + id + ", schoolId=" + schoolId + ", content=" + content + ", rate=" + rate + ", user="
				+ user + ", upVotes=" + upVotes + ", downVotes=" + downVotes + ", date=" + date + "]";
	}



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public int getUpVotes() {
		return upVotes;
	}
	public void setUpVotes(int upVotes) {
		this.upVotes = upVotes;
	}
	public int getDownVotes() {
		return downVotes;
	}
	public void setDownVotes(int downVotes) {
		this.downVotes = downVotes;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}


	public Long getSchoolId() {
		return schoolId;
	}


	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}


	
	public School getSchool() {
		return school;
	}



	public void setSchool(School school) {
		this.school = school;
	}
}
