package pl.pw.mini.Schoolify.modules;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.Date;

public class Comment2 {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long schoolId;
	private String content;
	private Integer rate;
	private String user;
	private Integer upVotes;
	private Integer downVotes;
	
	public Comment2() {}
	
	
	
	public Comment2(Long id, Long schoolId, String content, int rate, String user, int upVotes, int downVotes) {
		super();
		this.id = id;
		this.schoolId = schoolId;
		this.content = content;
		this.rate = rate;
		this.user = user;
		this.upVotes = upVotes;
		this.downVotes = downVotes;
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

	public Long getSchoolId() {
		return schoolId;
	}


	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}
}
