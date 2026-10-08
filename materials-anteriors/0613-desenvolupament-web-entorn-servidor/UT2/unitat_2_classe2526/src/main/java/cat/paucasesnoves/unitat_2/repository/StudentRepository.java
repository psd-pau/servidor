package cat.paucasesnoves.unitat_2.repository;

import cat.paucasesnoves.unitat_2.domain.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByFullNameContainingIgnoreCase(String keyword);
}
