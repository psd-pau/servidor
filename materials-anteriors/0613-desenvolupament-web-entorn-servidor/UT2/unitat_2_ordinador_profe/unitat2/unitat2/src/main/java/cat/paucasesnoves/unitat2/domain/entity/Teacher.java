package cat.paucasesnoves.unitat2.domain.entity;


import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entitat Teacher (Professor).
 * Exemple de @OneToMany ↔ @ManyToOne amb Course.
 * Un professor pot impartir molts cursos.
 */
@Entity
@Table(name = "TEACHER")
public class Teacher {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String fullName;

    private String department;

    /**
     * Relació @OneToMany:
     * - "mappedBy = teacher" → indica que la clau forana està a Course.
     * - Cascade i orphanRemoval → si eliminam un professor,
     * també s’eliminen els cursos que hi tenia associats.
     */
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Course> courses = new ArrayList<>();

    // Getters i setters
    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}