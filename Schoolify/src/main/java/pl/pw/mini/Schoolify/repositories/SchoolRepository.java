package pl.pw.mini.Schoolify.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import pl.pw.mini.Schoolify.modules.School;

@Repository
public interface SchoolRepository extends JpaRepository<School,Long>{
	List<School> findByName(String name); // not unique
	List<School> findByNameContaining(String search);
	List<School> findByNameStartsWithAndType(String prefix,String type);
	List<School> findByType(String type);
	List<School> findByLocalizationTown(String string);
	List<School> findByTypeAndLocalizationTown(String type, String town);
	List<School> findByNameStartsWithAndTypeAndLocalizationTown(String name, String type, String town);
	List<School> findByLocalizationLatOrLocalizationLon(Double lat,Double lon);
	List<School> findByNameStartsWithAndTypeAndLocalizationTownStartsWith(String name, String type, String town);
	List<School> findByTypeAndLocalizationTownStartsWith(String type, String town);
	List<School> findByLocalizationTownStartsWith(String town);
	
	
}
