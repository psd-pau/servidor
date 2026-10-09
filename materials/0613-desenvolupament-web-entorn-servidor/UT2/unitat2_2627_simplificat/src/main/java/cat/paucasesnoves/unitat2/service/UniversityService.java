package cat.paucasesnoves.unitat2.service;

import cat.paucasesnoves.unitat2.domain.entity.Course;
import cat.paucasesnoves.unitat2.domain.entity.Student;
import cat.paucasesnoves.unitat2.domain.entity.Teacher;
import cat.paucasesnoves.unitat2.repository.CourseRepository;
import cat.paucasesnoves.unitat2.repository.StudentRepository;
import cat.paucasesnoves.unitat2.repository.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/** Intermediari senzill; les consultes de BD són als repositoris. */
@Service
@Transactional // Manté oberta la transacció mentre accedim a les col·leccions lazy.
public class UniversityService {
    private final TeacherRepository teacherRepo;
    private final CourseRepository courseRepo;
    private final StudentRepository studentRepo;

    public UniversityService(TeacherRepository teacherRepo, CourseRepository courseRepo,
                             StudentRepository studentRepo) {
        this.teacherRepo = teacherRepo;
        this.courseRepo = courseRepo;
        this.studentRepo = studentRepo;
    }

    public Long createTeacher(Teacher teacher) {
        return teacherRepo.save(teacher).getId();
    }

    public Long createStudent(Student student) {
        return studentRepo.save(student).getId();
    }

    public Long createCourse(Course course) {
        return courseRepo.save(course).getId();
    }

    public void assignTeacher(Long courseId, Long teacherId) {
        Course course = courseRepo.findById(courseId).orElseThrow();
        Teacher teacher = teacherRepo.findById(teacherId).orElseThrow();
        course.setTeacher(teacher); // Course és el costat propietari de la clau forana.
        courseRepo.save(course);
    }

    public void enrollStudent(Long courseId, Long studentId) {
        Course course = courseRepo.findById(courseId).orElseThrow();
        Student student = studentRepo.findById(studentId).orElseThrow();
        if (!course.getStudents().contains(student)) {
            course.getStudents().add(student); // Costat propietari d'ENROLLMENT.
        }
        courseRepo.save(course);
    }

    // Publicam noms per observar el resultat, sense serialitzar el graf bidireccional.
    public List<String> getCoursesByTeacher(String name) {
        return courseRepo.findByTeacherFullName(name).stream().map(Course::getName).toList();
    }

    public List<String> getStudentsInCourse(Long id) {
        Course course = courseRepo.findById(id).orElseThrow();
        return course.getStudents().stream().map(Student::getFullName).toList();
    }

    public List<String> searchStudents(String keyword) {
        return studentRepo.findByFullNameContainingIgnoreCase(keyword).stream().map(Student::getFullName).toList();
    }
}
