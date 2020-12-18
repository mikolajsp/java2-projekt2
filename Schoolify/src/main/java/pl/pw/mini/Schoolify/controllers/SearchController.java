package pl.pw.mini.Schoolify.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.pw.mini.Schoolify.modules.School;
import pl.pw.mini.Schoolify.services.SchoolService;

@RestController
public class SearchController{
	
	@Autowired
	SchoolService ss;
	

	@GetMapping("/search")
	public ResponseEntity<List<School>> search(@RequestParam Map<String,String> allFilters){
		List<School> res = ss.filterSchools(allFilters);
		return ResponseEntity.ok(res);
	}

	
	
	
}
