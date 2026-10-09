package cat.paucasesnoves.unitat2.service;

import cat.paucasesnoves.unitat2.domain.entity.Course;
import cat.paucasesnoves.unitat2.domain.entity.Student;
import cat.paucasesnoves.unitat2.domain.entity.Teacher;
import cat.paucasesnoves.unitat2.dto.UniversityDto.*;
import cat.paucasesnoves.unitat2.exception.ResourceNotFoundException;
import cat.paucasesnoves.unitat2.repository.CourseRepository;
import cat.paucasesnoves.unitat2.repository.StudentRepository;
import cat.paucasesnoves.unitat2.repository.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.LinkedHashSet;
import java.util.List;

@Service
@Transactional(readOnly = true)
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

    @Transactional
    public TeacherView createTeacher(TeacherInput input) {
        Teacher teacher = new Teacher();
        teacher.setFullName(input.fullName());
        teacher.setDepartment(input.department());
        return teacherView(teacherRepo.saveAndFlush(teacher));
    }

    @Transactional
    public StudentView createStudent(StudentInput input) {
        Student student = new Student();
        student.setFullName(input.fullName());
        student.setEmail(input.email());
        return studentView(studentRepo.saveAndFlush(student));
    }

    @Transactional
    public CourseView createCourse(CourseInput input) {
        Course course = new Course();
        course.setName(input.name());
        course.setCredits(input.credits());

        // Assignam la relació des del costat propietari: Course.teacher.
        if (input.teacherId() != null) {
            Teacher teacher = teacherRepo.findById(input.teacherId())
                    .orElseThrow(() -> new ResourceNotFoundException("No existeix el professor " + input.teacherId()));
            course.setTeacher(teacher);
            teacher.getCourses().add(course);
        }

        // Course.students és el propietari de la taula ENROLLMENT.
        List<Long> ids = input.studentIds() == null ? List.of() : input.studentIds();
        for (Long id : new LinkedHashSet<>(ids)) {
            if (id == null) {
                throw new IllegalArgumentException("studentIds no pot contenir null.");
            }
            Student student = studentRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("No existeix l'estudiant " + id));
            course.getStudents().add(student);
            student.getCourses().add(course);
        }
        return courseView(courseRepo.saveAndFlush(course));
    }

    public List<CourseView> getCoursesByTeacher(String name) {
        // El DTO es construeix dins la transacció, mentre les relacions es poden carregar.
        return courseRepo.findByTeacherFullName(name).stream().map(this::courseView).toList();
    }

    public List<StudentView> getStudentsInCourse(Long id) {
        Course course = courseRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existeix el curs " + id));
        return course.getStudents().stream().map(this::studentView).toList();
    }

    // Recuperam la cerca IgnoreCase de la versió antiga i li donam una ruta.
    public List<StudentView> searchStudents(String keyword) {
        return studentRepo.findByFullNameContainingIgnoreCase(keyword).stream().map(this::studentView).toList();
    }

    private TeacherView teacherView(Teacher teacher) {
        return new TeacherView(teacher.getId(), teacher.getFullName(), teacher.getDepartment());
    }

    private StudentView studentView(Student student) {
        return new StudentView(student.getId(), student.getFullName(), student.getEmail());
    }

    private CourseView courseView(Course course) {
        return new CourseView(course.getId(), course.getName(), course.getCredits(),
                course.getTeacher() == null ? null : teacherView(course.getTeacher()),
                course.getStudents().stream().map(this::studentView).toList());
    }
}
