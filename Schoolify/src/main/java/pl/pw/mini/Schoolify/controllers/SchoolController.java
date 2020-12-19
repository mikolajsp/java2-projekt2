package pl.pw.mini.Schoolify.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.pw.mini.Schoolify.modules.School;
import pl.pw.mini.Schoolify.services.SchoolService;
@CrossOrigin
@RestController
@RequestMapping("/school")
public class SchoolController {
	
	@Autowired
	SchoolService ss;

	@GetMapping("id/{id}")
	public ResponseEntity<School> schoolById(@PathVariable("id") Long id) {
		School sch = ss.findById(id);
		return ResponseEntity.ok(sch);
	}
	@GetMapping("name/{name}")
	public ResponseEntity<List<School>> schoolByName(@PathVariable("name") String name) {
		List<School> sch = ss.findByName(name);
		return ResponseEntity.ok(sch);
	}
		
}
