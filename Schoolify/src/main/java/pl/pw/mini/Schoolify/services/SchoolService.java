package pl.pw.mini.Schoolify.services;

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
	
	
	public List<School> simpleFilterSchools(Map<String,String> allFilters){
		List<School> res;
		String name,type,town;
		if(allFilters.containsKey("name")){
			name = allFilters.get("name");
			if(allFilters.containsKey("town")) {
				town = allFilters.get("town");
				if(allFilters.containsKey("type")){
					type = allFilters.get("type");
					res = sr.findByNameStartingWithAndTypeAndLocalizationTown(name, type, town);
				}else {
					res = sr.findByNameStartingWithAndType(name,town);
				}
			}else {
				if(allFilters.containsKey("type")){
					type = allFilters.get("type");
					res = sr.findByNameStartingWithAndType(name, type);
				}else {
					res = sr.findByNameStartingWith(name);
				}
			}
		}else{
			if(allFilters.containsKey("town")){
				town = allFilters.get("town");
				if(allFilters.containsKey("type")){
					type = allFilters.get("type");
					res = sr.findByTypeAndLocalizationTown(type, town);
				}else {
					res = sr.findByLocalizationTown(town);
				}
			}else {
				if(allFilters.containsKey("type")){
					type = allFilters.get("type");
					res = sr.findByType(type);
				}else {
					res = sr.findAll();
				}
			}
		}
		return res;
		}
	
	public List<School> advancedFilterSchools(Map<String,String> allFilters){
		List<School> res = simpleFilterSchools(allFilters);
		List<School> pom;
		for(String key:allFilters.keySet()){
			switch(key) 
			{
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
			}
	}
		return res;
	}
	public School findById(Long id) {
		return this.sr.findById(id).orElse(null);
	}
	public School findByName(String name) {
		return this.sr.findByName(name);
	}
	
	
	
}
