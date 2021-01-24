package pl.pw.mini.Schoolify.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.pw.mini.Schoolify.modules.SearchResponseWrapper;
import pl.pw.mini.Schoolify.services.SchoolService;

@RestController
public class SearchController {

	@Autowired
	SchoolService ss;

	@CrossOrigin
	@GetMapping("/search")
	public ResponseEntity<SearchResponseWrapper> search(@RequestParam Map<String, String> allFilters) {
		SearchResponseWrapper srw = ss.search(allFilters);
		return ResponseEntity.ok(srw);
	}

}
