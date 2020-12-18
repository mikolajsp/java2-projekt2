package pl.pw.mini.Schoolify.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.pw.mini.Schoolify.modules.PositionFinder;
import pl.pw.mini.Schoolify.modules.School;
import pl.pw.mini.Schoolify.repositories.SchoolRepository;

@Service
public class SchoolService {
	
	@Autowired
	SchoolRepository sr;
	
	
	public List<School> simpleFilter(Map<String,String> allFilters){
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
	
	public List<School> advancedFilter(Map<String,String> allFilters){
		List <School> res = simpleFilter(allFilters);
		int ls = Integer.parseInt(allFilters.getOrDefault("studlower", "-1"));
		int us = Integer.parseInt(allFilters.getOrDefault("studupper", "10000"));
		int bl = Integer.parseInt(allFilters.getOrDefault("branlower", "-1"));
		int bu = Integer.parseInt(allFilters.getOrDefault("branupper", "10000"));
		String voi = allFilters.getOrDefault("voivodeship","");
		String county = allFilters.getOrDefault("county","");
		String comm = allFilters.getOrDefault("community","");
		List<School> ret = res.stream().filter(s -> us >= s.getStudents() && s.getStudents() >= ls).filter(
					s-> s.getBranches() >= bl && bu >= s.getBranches()).filter(
							s -> s.getLocalization().getVoivodeship().startsWith(voi)
							).filter(
									s -> s.getLocalization().getCounty().startsWith(county)
									).filter(s -> s.getLocalization().getCommunity().startsWith(comm)).collect(Collectors.toList());
		List<School> fin;
		if(allFilters.containsKey("origin")) {
			String or = allFilters.get("origin");
			int dist = Integer.parseInt(allFilters.get("distance"));
			Double[] coors = PositionFinder.findLonLat(or);
			fin = new ArrayList<>();
			for(School s: ret){
				double x = s.calculateDistance(coors[0], coors[1]);
				if(x < 1000*dist){
					fin.add(s);
				}
			}
			
		}else {
			return ret;
		}
		
		return fin;
				
	}

	public School findById(Long id) {
		return sr.findById(id).orElse(null);
	}
	public School findByName(String name) {
		return this.sr.findByName(name);
	}
	
	
	
	
	
	
	
}