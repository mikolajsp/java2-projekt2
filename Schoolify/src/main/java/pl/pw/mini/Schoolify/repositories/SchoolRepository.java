package pl.pw.mini.Schoolify.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import pl.pw.mini.Schoolify.modules.School;


public interface SchoolRepository extends JpaRepository<School,Long>{
	List<School> findByName(String name); // not unique
	List<School> findByNameContaining(String search);
	List<School> findByNameAndType(String prefix,String type);
	List<School> findByType(String type);
	List<School> findByLocalizationTownStartsWith(String string);
	List<School> findByTypeAndLocalizationTownStartsWith(String type, String town);
	List<School> findByNameAndTypeAndLocalizationTownStartsWith(String name, String type, String town);
	List<School> findByLocalizationLatOrLocalizationLon(Double lat,Double lon);
	
	
}
