package pl.pw.mini.Schoolify.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import pl.pw.mini.Schoolify.modules.Assesment;
import pl.pw.mini.Schoolify.modules.MultipleSchoolResponseWrapper;
import pl.pw.mini.Schoolify.modules.School;
import pl.pw.mini.Schoolify.modules.SingleSchoolResponseWrapper;
import pl.pw.mini.Schoolify.services.SchoolService;

@CrossOrigin
@RestController
@RequestMapping("/school")
public class SchoolController {

	@Autowired
	SchoolService ss;

	@GetMapping("id/{id}")
	public ResponseEntity<SingleSchoolResponseWrapper> schoolById(@PathVariable("id") Long id) {
		SingleSchoolResponseWrapper sch = ss.findById(id);
		return ResponseEntity.ok(sch);
	}

	@GetMapping("/ids/{schoolIDS}")
	public ResponseEntity<MultipleSchoolResponseWrapper> schoolsByIds(@PathVariable List<Long> schoolIDS) {
		MultipleSchoolResponseWrapper msrw = ss.schoolsByIds(schoolIDS);
		return ResponseEntity.ok(msrw);
	}

	@GetMapping("name/{name}")
	public ResponseEntity<MultipleSchoolResponseWrapper> schoolByName(@PathVariable("name") String name) {
		MultipleSchoolResponseWrapper sch = ss.findByName(name);
		return ResponseEntity.ok(sch);
	}

	@PostMapping("/survey")
	public void survey(@RequestParam Long id, @RequestParam Map<String, String> allFilters) {
		ss.newVoteSurvey(id, allFilters);
	}

}
