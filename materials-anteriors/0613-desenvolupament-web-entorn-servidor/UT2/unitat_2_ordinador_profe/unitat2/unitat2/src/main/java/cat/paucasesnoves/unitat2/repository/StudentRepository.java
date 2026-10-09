package cat.paucasesnoves.unitat2.repository;

import cat.paucasesnoves.unitat2.domain.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    // Estudiants que contenen una paraula al nom
    List<Student> findByFullNameContaining(String keyword);
}
