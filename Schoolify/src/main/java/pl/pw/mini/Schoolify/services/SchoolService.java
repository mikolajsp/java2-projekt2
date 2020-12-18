package pl.pw.mini.Schoolify.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.pw.mini.Schoolify.modules.School;
import pl.pw.mini.Schoolify.repositories.SchoolRepository;

@Service
public class SchoolService {
	
	@Autowired
	SchoolRepository sr;
	
	
	public List<School> filterSchools(Map<String,String> allFilters){
//		long start = System.currentTimeMillis();
		List<School> res = sr.findAll();
		List<School> pom = new ArrayList<>();
		for(String key:allFilters.keySet()){
			switch(key) 
			{
			case "name":
				pom = sr.findByNameStartingWith(allFilters.get("name"));
				res.retainAll(pom);
				break;
			case "type":
				pom = sr.findByType(allFilters.get("type")); 
				res.retainAll(pom);
				break;
			case "studlower":
				int ls = Integer.parseInt(allFilters.get("studlower"));
				pom = sr.findByStudentsGreaterThanEqual(ls);
				res.retainAll(pom);
				break;
			case "studupper":
				int us = Integer.parseInt(allFilters.get("studupper"));
				pom = sr.findByStudentsLessThanEqual(us);
				res.retainAll(pom);
				break;
			case "branlower":
				int bl = Integer.parseInt(allFilters.get("branlower"));
				pom = sr.findByBranchesGreaterThanEqual(bl);
				res.retainAll(pom);
				break;
			case "branupper":
				int bu = Integer.parseInt(allFilters.get("branupper"));
				pom = sr.findByBranchesLessThanEqual(bu);
				res.retainAll(pom);
				break;
			case "voivodeship":
				pom = sr.findByLocalizationVoivodeship(allFilters.get("voivodeship"));
				res.retainAll(pom);
				break;
			case "county":
				pom = sr.findByLocalizationCounty(allFilters.get("county"));
				res.retainAll(pom);
				break;
			case "community":
				pom = sr.findByLocalizationCommunity(allFilters.get("community"));
				res.retainAll(pom);
				break;
			case "town":
				pom = sr.findByLocalizationTown(allFilters.get("town"));
				res.retainAll(pom);
				break;
			}
		}
//		long fin = System.currentTimeMillis();
//		System.out.println(fin-start); for adwanced quieries it may take up to 7seconds TODO: fix it!
		return res;
		}
	
	
	public School findById(Long id) {
		return this.sr.findById(id).orElse(null);
	}
	public School findByName(String name) {
		return this.sr.findByName(name);
	}
	
	
	
}
