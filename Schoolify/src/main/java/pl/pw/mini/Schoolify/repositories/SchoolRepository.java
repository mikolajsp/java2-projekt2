package pl.pw.mini.Schoolify.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import pl.pw.mini.Schoolify.modules.School;


public interface SchoolRepository extends JpaRepository<School,Long>{
	List<School> findByName(String name); // not unique
	List<School> findByNameContaining(String search);
	List<School> findByNameStartsWithAndType(String prefix,String type);
	List<School> findByType(String type);
	List<School> findByLocalizationTown(String string);
	List<School> findByTypeAndLocalizationTown(String type, String town);
	List<School> findByNameStartsWithAndTypeAndLocalizationTown(String name, String type, String town);
	List<School> findByLocalizationLatOrLocalizationLon(Double lat,Double lon);
	
	
}
