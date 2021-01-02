package pl.pw.mini.Schoolify.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.pw.mini.Schoolify.modules.Assesment;
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
		School sch = ss.findById(id);
		Assesment a = ss.getAssesmentBySchoolId(id);
		SingleSchoolResponseWrapper ssrw = new SingleSchoolResponseWrapper();
		ssrw.setSchool(sch);
		ssrw.setAssesment(a);
		return ResponseEntity.ok(ssrw);
	}
	@GetMapping("name/{name}")
	public ResponseEntity<List<School>> schoolByName(@PathVariable("name") String name) {
		List<School> sch = ss.findByName(name);
		return ResponseEntity.ok(sch);
	}
		
}
