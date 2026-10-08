package cat.paucasesnoves.unitat_2.service;

import cat.paucasesnoves.unitat_2.domain.entity.Course;
import cat.paucasesnoves.unitat_2.domain.entity.Student;
import cat.paucasesnoves.unitat_2.domain.entity.Teacher;
import cat.paucasesnoves.unitat_2.repository.CourseRepository;
import cat.paucasesnoves.unitat_2.repository.StudentRepository;
import cat.paucasesnoves.unitat_2.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniversityService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public UniversityService(CourseRepository courseRepository,
                             StudentRepository studentRepository,
                             TeacherRepository teacherRepository){
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    //Crear entitats
    public Course createCourse(Course c){
        return courseRepository.save(c);
    }
    public Student createStudent(Student s){
        return studentRepository.save(s);
    }
    public Teacher createTeacher(Teacher t){
        return teacherRepository.save(t);
    }

    //Consultes
    public List<Course> getCoursesByTeacher(String teacherName){
        return courseRepository.findByTeacherFullName(teacherName);
    }

    public List<Student> getStudentsInCourse(Long courseId){
        return courseRepository.findById(courseId)
                .map(Course::getStudents)
                .orElseThrow();
    }
}
