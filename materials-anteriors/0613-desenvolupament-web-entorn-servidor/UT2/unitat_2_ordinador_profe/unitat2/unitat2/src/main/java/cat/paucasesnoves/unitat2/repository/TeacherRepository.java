package cat.paucasesnoves.unitat2.repository;



import cat.paucasesnoves.unitat2.domain.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Cada repositori és una interfície que hereta de JpaRepository<T, ID>.
 * Això ens dona CRUD complet i permet definir mètodes personalitzats.
 */

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    // Exemple de consulta derivada: trobar professors per departament
    List<Teacher> findByDepartment(String department);
}

