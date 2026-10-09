package cat.paucasesnoves.unitat2.service;

import cat.paucasesnoves.unitat2.domain.entity.Course;
import cat.paucasesnoves.unitat2.domain.entity.Student;
import cat.paucasesnoves.unitat2.domain.entity.Teacher;
import cat.paucasesnoves.unitat2.repository.CourseRepository;
import cat.paucasesnoves.unitat2.repository.StudentRepository;
import cat.paucasesnoves.unitat2.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UniversityService:
 * - Agrupa la lògica de negoci.
 * - Fa servir els repositoris per accedir a la BBDD.
 * - És la capa intermèdia entre Controller i Repository.
 */
@Service
public class UniversityService {

    private final TeacherRepository teacherRepo;
    private final CourseRepository courseRepo;
    private final StudentRepository studentRepo;

    public UniversityService(TeacherRepository teacherRepo, CourseRepository courseRepo, StudentRepository studentRepo) {
        this.teacherRepo = teacherRepo;
        this.courseRepo = courseRepo;
        this.studentRepo = studentRepo;
    }

    // Crear entitats
    public Teacher createTeacher(Teacher t) { return teacherRepo.save(t); }
    public Course createCourse(Course c) { return courseRepo.save(c); }
    public Student createStudent(Student s) { return studentRepo.save(s); }

    // Consultes
    public List<Course> getCoursesByTeacher(String teacherName) {
        return courseRepo.findByTeacherFullName(teacherName);
    }

    public List<Student> getStudentsInCourse(Long courseId) {
        return courseRepo.findById(courseId)
                .map(Course::getStudents) // accedim a la relació
                .orElseThrow();           // si no hi ha curs, llança excepció
    }
}