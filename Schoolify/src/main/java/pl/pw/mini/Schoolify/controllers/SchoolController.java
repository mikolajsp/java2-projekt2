package pl.pw.mini.Schoolify.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.pw.mini.Schoolify.modules.School;
import pl.pw.mini.Schoolify.repositories.SchoolRepository;

// Kontroler odpowiadający za zwracanie pojedynczej szkoły.

@RestController
public class SchoolController {
	
	@Autowired
	SchoolRepository sr;
	
	@GetMapping("school/id/{id}")
	public School schoolById(@PathVariable String id) {
		return sr.findById(Long.parseLong(id)).orElse(null);
	}
	@GetMapping("school/name/{name}")
	public School schoolByName(@PathVariable String name) {
		return sr.findByName(name);
	}
		
}
