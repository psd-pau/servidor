package cat.paucasesnoves.unitat_2.controller;

import cat.paucasesnoves.unitat_2.domain.entity.Course;
import cat.paucasesnoves.unitat_2.domain.entity.Student;
import cat.paucasesnoves.unitat_2.domain.entity.Teacher;
import cat.paucasesnoves.unitat_2.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/university")
public class UniversityController {
    private final UniversityService universityService;

    @Autowired
    public UniversityController(UniversityService universityService){
        this.universityService = universityService;
    }

    //Creació
    @PostMapping("/teachers")
    public Teacher addTeacher(@RequestBody Teacher teacher){
        return universityService.createTeacher(teacher);
    }
    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student){
        return universityService.createStudent(student);
    }
    @PostMapping("/courses")
    public Course addCourse(@RequestBody Course course){
        return universityService.createCourse(course);
    }


    // /teachers/{id}
    // teachers/{department}
    // /teachers/department/{department}
    // /teachers/{id}/courses
    //Consultes
    @GetMapping("/courses/teacher/name/{name}")
    public List<Course> getCoursesByTeacher(@PathVariable String name){
        return universityService.getCoursesByTeacher(name);
    }

    //alumnes d'un curs /courses/{id}/students
    // /courses/{id}
    // /courses/name/{name}
    @GetMapping("/courses/{id}/students")
    public List<Student> getStudentsInCourse(@PathVariable Long id){
        return universityService.getStudentsInCourse(id);
    }
}
