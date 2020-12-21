package pl.pw.mini.Schoolify.controllers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.pw.mini.Schoolify.modules.School;
import pl.pw.mini.Schoolify.modules.SearchResponseWrapper;
import pl.pw.mini.Schoolify.services.SchoolService;

@RestController
public class SearchController{
	
	@Autowired
	SchoolService ss;
	
	@CrossOrigin
	@GetMapping("/search")
	public ResponseEntity<SearchResponseWrapper> search(@RequestParam Map<String,String> allFilters){
		SearchResponseWrapper srw = ss.search(allFilters);
		return ResponseEntity.ok(srw);
	}
	
	@GetMapping("/mostpopular")
	public ResponseEntity<SearchResponseWrapper> mostPopular(@RequestParam Integer n){
		SearchResponseWrapper srw = ss.mostPopular(n);
		return ResponseEntity.ok(srw);
	}

	
}
