package pl.pw.mini.Schoolify.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.pw.mini.Schoolify.modules.Assesment;
import pl.pw.mini.Schoolify.modules.Comment;
import pl.pw.mini.Schoolify.modules.CommentResponseWrapper;
import pl.pw.mini.Schoolify.modules.ContentCrossExaminer;
import pl.pw.mini.Schoolify.modules.Localization;
import pl.pw.mini.Schoolify.modules.PositionFinder;
import pl.pw.mini.Schoolify.modules.School;
import pl.pw.mini.Schoolify.modules.SearchResponseWrapper;
import pl.pw.mini.Schoolify.modules.SingleSchoolResponseWrapper;
import pl.pw.mini.Schoolify.repositories.AssesmentRepository;
import pl.pw.mini.Schoolify.repositories.CommentRepository;
import pl.pw.mini.Schoolify.repositories.SchoolRepository;

@Service
public class SchoolService {
	
	@Autowired
	SchoolRepository sr;
	@Autowired
	CommentRepository cr;
	@Autowired
	AssesmentRepository ar;
	ContentCrossExaminer cce = new ContentCrossExaminer();
	
	public List<Comment> allComments(){
		return cr.findAll();
	}
	
	
	
	public void simpleFilter(Map<String,String> allFilters,SearchResponseWrapper srw){
		List<School> res;
		String name,type,town;
		if(allFilters.containsKey("name")){
			name = allFilters.get("name");
			if(allFilters.containsKey("town")) {
				town = allFilters.get("town");
				if(allFilters.containsKey("type")){
					type = allFilters.get("type");
					res = sr.findByNameStartsWithAndTypeAndLocalizationTownStartsWith(name, type, town);
				}else {
					res = sr.findByNameStartsWithAndType(name,town);
				}
			}else {
				if(allFilters.containsKey("type")){
					type = allFilters.get("type");
					res = sr.findByNameStartsWithAndType(name, type);
				}else {
					res = sr.findByNameContaining(name);
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
		srw.setSchoolList(res);
		}
	
	public List<SingleSchoolResponseWrapper> schoolsByIds(Iterable<Long> ids){
		List<School> ls = sr.findAllById(ids);
		List<SingleSchoolResponseWrapper> ssrwL = new ArrayList<>();
		for(School s: ls) {
			SingleSchoolResponseWrapper ssrw = new SingleSchoolResponseWrapper();
			ssrw.setSchool(s);
			ssrw.setAssesment(getAssesmentBySchoolId(s.getId()));
			ssrw.setAvg(avgRate(s.getId()));
			ssrw.setBestComment(bestComment(s.getId()));
			ssrwL.add(ssrw);
		}
		return ssrwL;
	}
	
	public void advancedFilter(Map<String,String> allFilters,SearchResponseWrapper srw){
		simpleFilter(allFilters,srw);
		List <School> res = srw.getSchoolList();
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
		if(allFilters.containsKey("origin")){
			List<School> fin = new ArrayList<>();
			String or = allFilters.get("origin");
			int dist = Integer.parseInt(allFilters.getOrDefault("distance","5"));
			Double[] coors = PositionFinder.findLonLat(or);
			srw.setOriginX(coors[1]);
			srw.setOriginY(coors[0]);
			srw.setAddress(or);
			for(School s: ret){
				double x = s.calculateDistance(coors[0], coors[1]);
				if(x < 1000*dist){
					fin.add(s);
				}
			}
			srw.setSchoolList(fin);
		}else {
			srw.setSchoolList(ret);
		}
				
	}

	public School findById(Long id) {
		School s = sr.findById(id).orElse(null);
		if(s != null) {
			s.setPopularity(s.getPopularity()+1);
			sr.save(s);
		}
		return s;
	}
	public List<School> findByName(String name) {
		List<School> sc = sr.findByName(name);
		for(School s:sc) {
			s.setPopularity(s.getPopularity()+1);
		}
		return sc;
		
	}
	
	
	public List<Comment> findCommentsById(Long id){
		return cr.findBySchoolId(id);
	}
	public Assesment getAssesmentBySchoolId(Long id) {
		return ar.findById(id).orElse(null);
	}
	
	public Integer avgRate(Long id) {
		List<Comment> lc = findCommentsById(id);
		if(lc.size() == 0) {
			return 0;
		}
		Integer sum = lc.stream().map(c->c.getRate()).reduce(0, Integer::sum)/lc.size();
		return sum;
	}
	public Comment bestComment(Long id) {
		List<Comment> lc = findCommentsById(id);
		if(lc.size() == 0) {
			return null;
		}
        Comment bc =  Collections.max(lc, Comparator.comparing(s -> s.getUpVotes() - s.getDownVotes()));
        return bc;

	}
	
	
	
	public CommentResponseWrapper getComments(Long id) {
		List<Comment> lc = findCommentsById(id);
		CommentResponseWrapper crw = new CommentResponseWrapper();
		crw.setComments(lc);
		return crw;
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
		ncom.setDate(LocalDate.now());
		ncom.setDownVotes(0);
		ncom.setUpVotes(0);
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
	
	public void newVoteSurvey(Long id, Map<String,String> vote) {
		Assesment schoolAssesment = ar.findById(id).orElse(null);
		Integer votes = schoolAssesment.getVotes_number();
		if(votes==0) {
			votes++;
		}
		Double newEducational = (schoolAssesment.getEducational()*votes + Double.parseDouble(vote.get("educational")))/(votes+1);
		schoolAssesment.setEducational(newEducational);
		Double newFriendliness = (schoolAssesment.getFriendliness()*votes + Double.parseDouble(vote.get("friendliness")))/(votes+1);
		schoolAssesment.setFriendliness(newFriendliness);
		Double newIntrests = (schoolAssesment.getIntrests()*votes + Double.parseDouble(vote.get("intrests")))/(votes+1);
		schoolAssesment.setIntrests(newIntrests);
		Double newLowPrice = (schoolAssesment.getLowPrice()*votes + Double.parseDouble(vote.get("lowPrice")))/(votes+1);
		schoolAssesment.setLowPrice(newLowPrice);
		Double newCommute = (schoolAssesment.getCommute()*votes + Double.parseDouble(vote.get("commute")))/(votes+1);
		schoolAssesment.setCommute(newCommute);
		schoolAssesment.setVotes_number(votes+1);
		ar.save(schoolAssesment);
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
	
	public void filterOutliers(SearchResponseWrapper x) {
		List<School> res = x.getSchoolList();
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
		x.setStd(sd);
		Double mean = calculateAverage(distances);
		Double rule = 1.2;
		List<School> filteredSchools = new ArrayList<>();
		for(int i = 0; i < res.size(); i++) {
			if(distances.get(i) <= mean + sd*rule && distances.get(i) >= mean - sd*rule ) {
				filteredSchools.add(res.get(i));
			}
		}
		x.setSchoolList(filteredSchools);
		
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
	
	public void setCenterUtil(SearchResponseWrapper srw){
		Double[] center = calculateCenter(srw.getSchoolList());
		srw.setX_center(center[0]);
		srw.setY_center(center[1]);
	}
		
	public SearchResponseWrapper search(Map<String, String> allFilters){
		SearchResponseWrapper srw = new SearchResponseWrapper();
		String[] simple = {"town","name","type"};
		Set<String> simple_filters = new HashSet<String>(Arrays.asList(simple));
		if(simple_filters.containsAll(allFilters.keySet())){
			simpleFilter(allFilters,srw);
		}else {
			advancedFilter(allFilters,srw);
		}
		filterOutliers(srw);
		setCenterUtil(srw);
		setMostPopular(srw,10);
		return srw;
	}
	

	public void setMostPopular(SearchResponseWrapper srw, Integer n){
		List<School> byPop = srw.getSchoolList().stream().sorted(Comparator.comparingInt(s -> -s.getPopularity())).collect(Collectors.toList());
		srw.setMostPopular(byPop.subList(Math.max(0, byPop.size()- n) , byPop.size()));
	}
	
}
