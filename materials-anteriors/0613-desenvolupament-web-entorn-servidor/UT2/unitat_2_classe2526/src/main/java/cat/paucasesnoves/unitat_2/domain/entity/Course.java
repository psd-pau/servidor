package cat.paucasesnoves.unitat_2.domain.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "COURSE")
public class Course {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private int credits;

    /*
        Relació és @ManyToOne
        -molts de cursos poden tenir el mateix profe
        -La clau forana ha d'estar a COURSE(columna teacher_id)
     */
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    /*
        Relació @ManyToMany
        -un curs pot tenir molts d'estudiants
        -un estudiant pot tenir molts de cursos
        -necesitam taula de relació
        -Taula de relació ENROLLMENT, amb fk a course_id, student_id
     */
    @ManyToMany
    @JoinTable(
            name = "ENROLLMENT",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> students = new ArrayList<>();

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
