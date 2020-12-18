package pl.pw.mini.Schoolify.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import pl.pw.mini.Schoolify.modules.School;


public interface SchoolRepository extends JpaRepository<School,Long>{
	School findByName(String name);
	List<School> findByNameStartingWith(String prefix);
	List<School> findByNameStartingWithAndType(String prefix,String type);
	List<School> findByType(String type);
	List<School> findByLocalizationVoivodeship(String voi);
	List<School> findByLocalizationCounty(String cou);
	List<School> findByLocalizationCommunity(String comm);
	List<School> findByStudentsGreaterThanEqual(int lower);
	List<School> findByStudentsLessThanEqual(int upper);
	List<School> findByBranchesGreaterThanEqual(int lower);
	List<School> findByBranchesLessThanEqual(int upper);
	List<School> findByLocalizationTown(String string);
	List<School> findByTypeAndLocalizationTown(String type, String town);
	List<School> findByNameStartingWithAndTypeAndLocalizationTown(String name, String type, String town);
	
	
}
