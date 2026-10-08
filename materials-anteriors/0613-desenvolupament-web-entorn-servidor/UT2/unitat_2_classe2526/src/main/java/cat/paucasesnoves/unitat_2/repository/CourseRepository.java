package cat.paucasesnoves.unitat_2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cat.paucasesnoves.unitat_2.domain.entity.Course;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    //Llista cursos amb més de X credits
    List<Course> findByCreditsGreaterThan(int credits);

    //Cursos impartits per un professor concret (via relació)
    List<Course> findByTeacherFullName(String fullName);
}
