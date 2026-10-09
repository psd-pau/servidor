package cat.paucasesnoves.unitat2.controller;

import cat.paucasesnoves.unitat2.domain.entity.Course;
import cat.paucasesnoves.unitat2.domain.entity.Student;
import cat.paucasesnoves.unitat2.domain.entity.Teacher;
import cat.paucasesnoves.unitat2.service.UniversityService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/** Proves de relacions: tornam IDs o noms per mantenir la resposta senzilla. */
@RestController
@RequestMapping("/university")
public class UniversityController {
    private final UniversityService service;

    public UniversityController(UniversityService service) {
        this.service = service;
    }

    @PostMapping("/teachers")
    public Long addTeacher(@RequestBody Teacher teacher) {
        return service.createTeacher(teacher);
    }

    @PostMapping("/students")
    public Long addStudent(@RequestBody Student student) {
        return service.createStudent(student);
    }

    @PostMapping("/courses")
    public Long addCourse(@RequestBody Course course) {
        return service.createCourse(course);
    }

    @PostMapping("/courses/{courseId}/teacher/{teacherId}")
    public void assignTeacher(@PathVariable Long courseId, @PathVariable Long teacherId) {
        service.assignTeacher(courseId, teacherId);
    }

    @PostMapping("/courses/{courseId}/students/{studentId}")
    public void enrollStudent(@PathVariable Long courseId, @PathVariable Long studentId) {
        service.enrollStudent(courseId, studentId);
    }

    @GetMapping("/courses/teacher/{name}")
    public List<String> getCoursesByTeacher(@PathVariable String name) {
        return service.getCoursesByTeacher(name);
    }

    @GetMapping("/courses/{id}/students")
    public List<String> getStudentsInCourse(@PathVariable Long id) {
        return service.getStudentsInCourse(id);
    }

    @GetMapping("/students/search")
    public List<String> searchStudents(@RequestParam String keyword) {
        return service.searchStudents(keyword);
    }
}
