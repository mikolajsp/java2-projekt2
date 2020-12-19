package pl.pw.mini.Schoolify.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.pw.mini.Schoolify.modules.Comment;
import pl.pw.mini.Schoolify.services.SchoolService;

@RestController
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	SchoolService ss;
	
	@PostMapping("/add")
	public void addComment(Long schoolId, String content, String username, Integer rate){
		if(schoolId != null && content != null) {
			ss.addComment(schoolId, content, username, rate);
		}
	}	
	
	@GetMapping("/postid/{postid}")
	public ResponseEntity<List<Comment>> getCommentsForSchoolId(@PathVariable("postid") Long schoolId){
		return ResponseEntity.ok(ss.findCommentsById(schoolId));
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
