package cat.paucasesnoves.unitat2.domain.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entitat Course (Assignatura).
 * Exemple de @ManyToOne (Professor) i @ManyToMany (Estudiants).
 */
@Entity
@Table(name = "COURSE")
public class Course {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private int credits;

    /**
     * Relació @ManyToOne:
     * - Molts cursos poden tenir el mateix professor.
     * - La clau forana queda a la taula COURSE (columna teacher_id).
     */
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    /**
     * Relació @ManyToMany:
     * - Un curs pot tenir molts estudiants.
     * - Un estudiant pot estar matriculat a molts cursos.
     * - Necessitam una taula intermèdia (ENROLLMENT) amb dues claus foranes.
     */
    @ManyToMany
    @JoinTable(
            name = "ENROLLMENT",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> students = new ArrayList<>();

    // Getters i setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }
    public Teacher getTeacher() { return teacher; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }
    public List<Student> getStudents() { return students; }
    public void setStudents(List<Student> students) { this.students = students; }
}
