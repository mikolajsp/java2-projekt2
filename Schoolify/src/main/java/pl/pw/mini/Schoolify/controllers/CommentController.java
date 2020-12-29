package pl.pw.mini.Schoolify.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pw.mini.Schoolify.modules.CommentResponseWrapper;
import pl.pw.mini.Schoolify.services.SchoolService;

@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {
	
	@Autowired
	SchoolService ss;
	
	@PostMapping("/add")
	public void addComment(Long schoolId, String content, String username, Integer rate){
		System.out.println(schoolId);
		System.out.println(content);
		System.out.println(username);
		if(schoolId != null && content != null) {
			ss.addComment(schoolId, content, username, rate);
		}
	}	
	@GetMapping("/pid/{pid}")
	public ResponseEntity<CommentResponseWrapper> getCommentsForSchoolId(@PathVariable("pid") Integer pid){
		CommentResponseWrapper crw = ss.getComments(Long.valueOf(pid));
		return ResponseEntity.ok(crw);
	}
	
	@PostMapping("/upvote/{commentid}")
	public void upVoteComment(@PathVariable("commentid") Long id) {
		ss.upVoteComment(id);
	}
	
	@PostMapping("/downvote/{commentid}")
	public void downVoteComment(@PathVariable("commentid") Long id) {
		ss.downVoteComment(id);
	}
	
	
	
}
