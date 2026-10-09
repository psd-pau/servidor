package cat.paucasesnoves.unitat2.controller;

import cat.paucasesnoves.unitat2.domain.entity.Course;
import cat.paucasesnoves.unitat2.domain.entity.Student;
import cat.paucasesnoves.unitat2.domain.entity.Teacher;
import cat.paucasesnoves.unitat2.service.UniversityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UniversityController:
 * - Defineix els endpoints REST.
 * - Crida a UniversityService per la lògica.
 * - Mostra als alumnes com exposar relacions amb JSON.
 */
@RestController
@RequestMapping("/university")
public class UniversityController {

    private final UniversityService service;

    public UniversityController(UniversityService service) {
        this.service = service;
    }

    // --- CREACIÓ ---
    @PostMapping("/teachers")
    public Teacher addTeacher(@RequestBody Teacher t) {
        return service.createTeacher(t);
    }

    @PostMapping("/courses")
    public Course addCourse(@RequestBody Course c) {
        return service.createCourse(c);
    }

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student s) {
        return service.createStudent(s);
    }

    // --- CONSULTES ---
    // Llistar cursos d'un professor pel seu nom
    @GetMapping("/courses/teacher/{name}")
    public List<Course> getCoursesByTeacher(@PathVariable String name) {
        return service.getCoursesByTeacher(name);
    }

    // Llistar alumnes matriculats a un curs
    @GetMapping("/courses/{id}/students")
    public List<Student> getStudentsInCourse(@PathVariable Long id) {
        return service.getStudentsInCourse(id);
    }
}
