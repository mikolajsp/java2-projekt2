package pl.pw.mini.Schoolify.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import pl.pw.mini.Schoolify.modules.School;

public interface SchoolRepository extends JpaRepository<School,Long>{
	School findByName(String name);
	List<School> findByNameStartingWith(String prefix);
	List<School> findByType(String type);
//	List<School> findByLocalizationVoivodeship(String voi);
//	List<School> findByLocalizationCounty(String cou);
//	List<School> findByLocalizationCommunity(String comm);
	List<School> findByStudents(String stud);
	List<School> findByBranches(String branch);
}
