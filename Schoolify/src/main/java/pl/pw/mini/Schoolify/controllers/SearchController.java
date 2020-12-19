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
import pl.pw.mini.Schoolify.services.SchoolService;

@RestController
public class SearchController{
	
	@Autowired
	SchoolService ss;
	
	@SuppressWarnings("unlikely-arg-type")
	@CrossOrigin
	@GetMapping("/search")
	public ResponseEntity<List<School>> search(@RequestParam Map<String,String> allFilters){
		String[] simple = {"town","name","type"};
		List<School> res;
		Set<String> simple_filters = new HashSet<String>(Arrays.asList(simple));
		if(simple_filters.contains(allFilters)){
			res = ss.simpleFilter(allFilters);
		}else {
			res = ss.advancedFilter(allFilters);
		}
		return ResponseEntity.ok(res);
	}

	
	
	
}
