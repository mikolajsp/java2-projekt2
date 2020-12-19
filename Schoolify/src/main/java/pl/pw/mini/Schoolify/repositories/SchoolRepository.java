package pl.pw.mini.Schoolify.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import pl.pw.mini.Schoolify.modules.School;


public interface SchoolRepository extends JpaRepository<School,Long>{
	List<School> findByName(String name); // not unique
	List<School> findByNameContaining(String search);
	List<School> findByNameContainingAndType(String prefix,String type);
	List<School> findByType(String type);
	List<School> findByLocalizationTown(String string);
	List<School> findByTypeAndLocalizationTown(String type, String town);
	List<School> findByNameContainingAndTypeAndLocalizationTown(String name, String type, String town);
	
	
}
