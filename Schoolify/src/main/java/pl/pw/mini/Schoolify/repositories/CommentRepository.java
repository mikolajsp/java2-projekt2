package pl.pw.mini.Schoolify.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pw.mini.Schoolify.modules.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	List<Comment> findBySchoolId(Long schoolId);

}
