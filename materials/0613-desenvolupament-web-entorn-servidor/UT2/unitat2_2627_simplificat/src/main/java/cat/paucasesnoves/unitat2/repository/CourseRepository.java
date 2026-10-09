package cat.paucasesnoves.unitat2.repository;

import cat.paucasesnoves.unitat2.domain.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    // Cursos amb més de X crèdits
    List<Course> findByCreditsGreaterThan(int credits);

    // Cursos impartits per un professor concret (via relació)
    List<Course> findByTeacherFullName(String fullName);
}
