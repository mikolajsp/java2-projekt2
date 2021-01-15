package pl.pw.mini.Schoolify.modules;

public class SingleSchoolResponseWrapper {
	private School school;
	private Integer avg;
	private Comment bestComment;
	public School getSchool() {
		return school;
	}
	public void setSchool(School school) {
		this.school = school;
	}
	public Integer getAvg() {
		return avg;
	}
	public void setAvg(Integer avg) {
		this.avg = avg;
	}
	public Comment getBestComment() {
		return bestComment;
	}
	public void setBestComment(Comment bestComment) {
		this.bestComment = bestComment;
	}


	
}
