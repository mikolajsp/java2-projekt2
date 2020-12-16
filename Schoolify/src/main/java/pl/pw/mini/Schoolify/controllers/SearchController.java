package pl.pw.mini.Schoolify.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.pw.mini.Schoolify.modules.School;
import pl.pw.mini.Schoolify.repositories.SchoolRepository;

@RestController
public class SearchController{
	
	@Autowired
	SchoolRepository sr;
	
	
	
	@GetMapping("/search")
	public Iterable<School> search(@RequestParam Map<String,String> allFilters){
		List<School> res = sr.findAll();
		List<School> pom = new ArrayList<>();
		for(String key:allFilters.keySet()){
			System.out.println(key);
			switch(key) 
			{
			case "name":
				pom = sr.findByNameStartingWith(allFilters.get("name"));
				break;
			case "type":
				pom = sr.findByType(allFilters.get("type"));
				break;
			case "voivodeship":
				pom = sr.findByLocalizationVoivodeship(allFilters.get("voivodeship"));
				break;
			case "county":
				pom = sr.findByLocalizationCounty(allFilters.get("county"));
				break;
			case "community":
				pom = sr.findByLocalizationCommunity(allFilters.get("community"));
				break;
			case "students":
				pom = sr.findByStudents(allFilters.get("students"));
				break;
			case "branches":
				pom = sr.findByBranches(allFilters.get("branches"));	
				break;
			}
			res.retainAll(pom);
		}
		//x.getLocalization().getLongitude()
		System.out.println(res);
		return res;
	}
}
