package pl.pw.mini.Schoolify.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.pw.mini.Schoolify.modules.Comment;
import pl.pw.mini.Schoolify.modules.ContentCrossExaminer;
import pl.pw.mini.Schoolify.modules.Localization;
import pl.pw.mini.Schoolify.modules.PositionFinder;
import pl.pw.mini.Schoolify.modules.School;
import pl.pw.mini.Schoolify.modules.SearchResponseWrapper;
import pl.pw.mini.Schoolify.repositories.CommentRepository;
import pl.pw.mini.Schoolify.repositories.SchoolRepository;

@Service
public class SchoolService {
	
	@Autowired
	SchoolRepository sr;
	@Autowired
	CommentRepository cr;
	ContentCrossExaminer cce = new ContentCrossExaminer();
	
	public List<School> simpleFilter(Map<String,String> allFilters){
		List<School> res;
		String name,type,town;
		if(allFilters.containsKey("name")){
			name = allFilters.get("name");
			if(allFilters.containsKey("town")) {
				town = allFilters.get("town");
				if(allFilters.containsKey("type")){
					type = allFilters.get("type");
					res = sr.findByNameAndTypeAndLocalizationTownStartsWith(name, type, town);
				}else {
					res = sr.findByNameAndType(name,town);
				}
			}else {
				if(allFilters.containsKey("type")){
					type = allFilters.get("type");
					res = sr.findByNameAndType(name, type);
				}else {
					res = sr.findByName(name);
				}
			}
		}else{
			if(allFilters.containsKey("town")){
				town = allFilters.get("town");
				if(allFilters.containsKey("type")){
					type = allFilters.get("type");
					res = sr.findByTypeAndLocalizationTownStartsWith(type, town);
				}else {
					res = sr.findByLocalizationTownStartsWith(town);
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
		List<School> ret = res.stream().filter(s -> s.getStudents() == null || (s.getStudents() != null && us >= s.getStudents() && s.getStudents() >= ls)).filter(
					s-> s.getBranches() == null || (s.getBranches() != null && s.getBranches() >= bl && bu >= s.getBranches())).filter(
							s -> s.getLocalization().getVoivodeship().startsWith(voi)
							).filter(
									s -> s.getLocalization().getCounty() == null || s.getLocalization().getCounty().startsWith(county)
									).filter(s -> s.getLocalization().getCommunity() == null || s.getLocalization().getCommunity().startsWith(comm)).collect(Collectors.toList());
		List<School> fin;
		if(allFilters.containsKey("origin")) {
			String or = allFilters.get("origin");
			int dist = Integer.parseInt(allFilters.getOrDefault("distance","5"));
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
	public List<School> findByName(String name) {
		return this.sr.findByName(name);
	}
	
	
	public List<Comment> findCommentsById(Long id){
		return cr.findBySchoolId(id);
	}
	
	
	public void addComment(Long sid, String cont, String us, int rate) {
		if(!cce.checkBadWords(cont)){
			System.out.println("Comment violates our policy.");
			return;
		}
		Comment ncom = new Comment();
		ncom.setSchoolId(sid);
		ncom.setContent(cont);
		ncom.setUser(us);
		ncom.setRate(rate);
		ncom.setDate(new Date());
		cr.save(ncom);
	}
	
	public Comment getCommentByItsId(Long id) {
		return cr.findById(id).orElse(null);
	}
	
	public void upVoteComment(Long id) {
		Comment c = getCommentByItsId(id);
		if(c != null) {
			c.setUpVotes(c.getUpVotes()+1);
			cr.save(c);
		}
		
	}
	
	public void downVoteComment(Long id) {
		Comment c = getCommentByItsId(id);
		if(c != null) {
			c.setDownVotes(c.getDownVotes()+1);
			cr.save(c);
		}
		
	}
	
	public static Double calculateSD(List<Double> ld)
    {
        Double sum = 0.0, standardDeviation = 0.0;
        int length = ld.size();

        for(Double num : ld) {
        	if(num != null) {
        		sum += num;
        	}
            
        }

        Double mean = sum/length;

        for(Double num: ld) {
        	if(num != null) {
                standardDeviation += Math.pow(num - mean, 2);
        	}
        }

        return Math.sqrt(standardDeviation/length);
    }
	
	private Double calculateAverage(List <Double> marks) {
		  Double sum = 0.0;
		  if(!marks.isEmpty()) {
		    for (Double mark : marks) {
		    	if(mark != null) {
			        sum += mark;
		    	}
		    }
		    return sum.doubleValue() / marks.size();
		  }
		  return sum;
	}
	
	
	public List<School> filterOutliers(List<School> res) {
		List<Double> lon = res.stream().map(School::getLocalization).
				map(Localization::getLon).collect(Collectors.toList());
		List<Double> lat = res.stream().map(School::getLocalization).
				map(Localization::getLat).collect(Collectors.toList());
		Double lonStd = calculateSD(lon);
		Double latStd = calculateSD(lat);
		Double meanLon = calculateAverage(lon);
		Double meanLat = calculateAverage(lat);
		final Double numberOfStds = 3.0;
		List<School> resFiltered = res.stream().filter(s->{
			Localization loc = s.getLocalization();
			if(loc == null) {
				return false;
			}
			Double slon = loc.getLon();
			Double slat = loc.getLat();
			if(slon == null || slat == null) {
				return false;
			}
			return (meanLat + numberOfStds*lonStd  >  slat && slat > meanLat - numberOfStds*lonStd 
					&& meanLon + numberOfStds*latStd >  slon && slon > meanLon - numberOfStds*latStd );
}
		).collect(Collectors.toList());
		return resFiltered;
	}
	
	
	public Double calculateDistance(Double lat1,Double lon1,Double lat2,Double lon2) {
		int R = 6370000; // earth radius
		double pom = Math.PI/180;
		double ph1 = lat1 * pom ;
		double ph2 = lat2 * pom;
		double deltaphi = (lat2 - lat1) * pom;
		double deltalambda = (lon2-lon1) * pom;
		double a = Math.sin(deltaphi/2) * Math.sin(deltaphi/2) +
		          Math.cos(ph1) * Math.cos(ph2) *
		          Math.sin(deltalambda/2) * Math.sin(deltalambda/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		return  R * c;
	}
	
	
	
	
	public List<School> filterOutliers2(List<School> res) {
		List<Double> lon = res.stream().map(School::getLocalization).
				map(Localization::getLon).collect(Collectors.toList());
		List<Double> lat = res.stream().map(School::getLocalization).
				map(Localization::getLat).collect(Collectors.toList());
		Double meanLon = calculateAverage(lon);
		Double meanLat = calculateAverage(lat);
		List<Double> distances = new ArrayList<>();
		for(School s: res) {
			distances.add(s.calculateDistance(meanLat, meanLon));
		}
		Double sd = calculateSD(distances);
		Double mean = calculateAverage(distances);
		Double rule = 3.0;
		List<School> filteredSchools = new ArrayList<>();
		for(int i = 0; i < res.size(); i++) {
			if(distances.get(i) <= mean + sd*rule && distances.get(i) >= mean - sd*rule ) {
				filteredSchools.add(res.get(i));
			}
		}
		System.out.println(res.size()-filteredSchools.size());
		return filteredSchools;
		
	}
	
	
	
	
	
	
	
	public Double[] calculateCenter(List<School> res){
		Double[] positionCenter = new Double[2];
		List<Double> lonFiltered = res.stream().map(School::getLocalization).
				map(Localization::getLon).collect(Collectors.toList());
		List<Double> latFiltered = res.stream().map(School::getLocalization).
				map(Localization::getLat).collect(Collectors.toList());
		if(res.size() == 0) {
			// set pC as a "middle" of Poland if there are no Schools matching
				positionCenter[0] = 52.06;
				positionCenter[1] = 19.26;
		}else {
			positionCenter[0] = calculateAverage(latFiltered); 
			positionCenter[1] = calculateAverage(lonFiltered);
		}
		return positionCenter;	
		}
	
	public Double[] extremsLonLat(List<School> fSchools) {
		Double res[] = {54.0, 49.0, 24.0, 14.0}; // min lat max lat min lon max lon for PL (swapped accordingly to perform min max) 
		for(School s : fSchools){
			Localization l = s.getLocalization();
			Double lon = l.getLon();
			Double lat = l.getLat();
			if(lat < res[0]) {
				res[0] = lat;
			}
			if(lat > res[1]){
				res[1] = lat;
			}
			if(lon < res[2]) {
				res[2] = lon;
			}
			if(lon > res[3]) {
				res[3] = lon;			
			}
		}
		return res;
	}
	
	
	
	
	public SearchResponseWrapper search(Map<String, String> allFilters){
		SearchResponseWrapper srw = new SearchResponseWrapper();
		String[] simple = {"town","name","type"};
		List<School> res;
		Set<String> simple_filters = new HashSet<String>(Arrays.asList(simple));
		if(simple_filters.containsAll(allFilters.keySet())){
			res = simpleFilter(allFilters);
		}else {
			res = advancedFilter(allFilters);
		}
		List<School> resWithoutOutliers = filterOutliers2(res);
		Double[] center = calculateCenter(resWithoutOutliers);
		srw.setSchoolList(resWithoutOutliers);
		srw.setX_center(center[0]);
		srw.setY_center(center[1]);
//		if(resWithoutOutliers.size() == 0) {
//			srw.setMinLat(49.0);
//			srw.setMaxLat(54.0);
//			srw.setMinLon(14.0);
//			srw.setMaxLon(24.0);
//		}else {
//			Double[] extrems = extremsLonLat(resWithoutOutliers);
//			srw.setMinLat(extrems[0]);
//			srw.setMaxLat(extrems[1]);
//			srw.setMinLon(extrems[2]);
//			srw.setMaxLon(extrems[3]);
//		}
		return srw;
	}
	
	public void cleanCrap() {
		List<School> schools = sr.findAll();
		List<School> crappSchools = schools.stream().
				filter(x -> x.getLocalization().getLat() == null || x.getLocalization().getLon() == null).
				collect(Collectors.toList());
		List<School> crappSchools2 = schools.stream().
				filter( x ->
				(x.getLocalization().getLat() == 0.0 || x.getLocalization().getLon() == 0.0) && x.getId() <= 55172 ).
				collect(Collectors.toList());
		sr.deleteAll(crappSchools);
		sr.deleteAll(crappSchools2);
	}
	
	
	public void calcateMissingCoords() {
		cleanCrap();
		List<School> schools = sr.findAll();
		List<School> to_add = schools.stream().
		filter( x ->
		x.getLocalization().getLat() == 0.0 || x.getLocalization().getLon() == 0.0).
		collect(Collectors.toList());
//		List<School> to_add = schools.stream().
//		filter( x ->
//		x.getId() >= 55181).
//		collect(Collectors.toList());
		for(School s : to_add) {
			String point = s.getLocalization().getStreet()+ " " + s.getLocalization().getHouseNumber() + ", " + s.getLocalization().getTown() ;
			Double position[] = PositionFinder.findLonLat(point);
			Localization loc = s.getLocalization();
			loc.setLat(position[1]);
			loc.setLon(position[0]);
			s.setLocalization(loc);
		}
		sr.saveAll(to_add);
	}
// nie pytaj xD
//	public void fixFuckup(){
//		List<School> schools = sr.findAll();
//		List<School> uni = schools.stream().filter(x -> x.getId() >= 55181).collect(Collectors.toList());
//		for(School s : uni) {
//			Double lat = s.getLocalization().getLat();
//			Double lon = s.getLocalization().getLon();
//			s.getLocalization().setLat(lon);
//			s.getLocalization().setLon(lat);
//		}
//		sr.saveAll(uni);
//	}
	
	
	public void fixLinks() {
		List<School> s = sr.findAll();
		List<School> withWebsites = s.stream().filter(x -> x.getContact() != null && x.getContact().getWebsite() != null).collect(Collectors.toList());
		List<School> badLinks = withWebsites.stream().filter(x -> x.getContact().getWebsite().startsWith("://")).
					collect(Collectors.toList());
		for(School sx : badLinks) {
				String web = sx.getContact().getWebsite();
				sx.getContact().setWebsite(web.substring(3));
		}
		sr.saveAll(badLinks);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
