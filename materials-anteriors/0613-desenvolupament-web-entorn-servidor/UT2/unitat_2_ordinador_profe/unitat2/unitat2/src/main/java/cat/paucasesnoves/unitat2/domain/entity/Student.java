package cat.paucasesnoves.unitat2.domain.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entitat Student (Estudiant).
 * Exemple de @ManyToMany inversa (enrolments en cursos).
 */
@Entity
@Table(name = "STUDENT")
public class Student {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String fullName;

    private String email;

    /**
     * Relació @ManyToMany inversa:
     * - L'altre costat de la relació és Course.students.
     * - "mappedBy" indica que aquesta entitat no té la taula intermèdia,
     *   sinó que l'aprofita de Course.
     */
    @ManyToMany(mappedBy = "students")
    private List<Course> courses = new ArrayList<>();

    // Getters i setters
    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public List<Course> getCourses() { return courses; }
    public void setCourses(List<Course> courses) { this.courses = courses; }
}