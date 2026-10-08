package cat.paucasesnoves.unitat_2.repository;

import cat.paucasesnoves.unitat_2.domain.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findByDepartment(String department);
}
