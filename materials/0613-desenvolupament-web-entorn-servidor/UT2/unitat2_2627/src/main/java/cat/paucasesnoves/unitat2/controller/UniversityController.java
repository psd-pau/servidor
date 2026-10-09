package cat.paucasesnoves.unitat2.controller;

import cat.paucasesnoves.unitat2.dto.UniversityDto.*;
import cat.paucasesnoves.unitat2.service.UniversityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/** Relacions i publicació amb DTO: bloc posterior al CRUD de llibres. */
@RestController
@RequestMapping({"/university", "/api/university"})
public class UniversityController {
    private final UniversityService service;

    public UniversityController(UniversityService service) {
        this.service = service;
    }

    @PostMapping("/teachers")
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherView addTeacher(@Valid @RequestBody TeacherInput input) {
        return service.createTeacher(input);
    }

    @PostMapping("/students")
    @ResponseStatus(HttpStatus.CREATED)
    public StudentView addStudent(@Valid @RequestBody StudentInput input) {
        return service.createStudent(input);
    }

    @PostMapping("/courses")
    @ResponseStatus(HttpStatus.CREATED)
    public CourseView addCourse(@Valid @RequestBody CourseInput input) {
        return service.createCourse(input);
    }

    // Ruta de la còpia del professor i àlies de la còpia de classe.
    @GetMapping({"/courses/teacher/{name}", "/courses/teacher/name/{name}"})
    public List<CourseView> getCoursesByTeacher(@PathVariable String name) {
        return service.getCoursesByTeacher(name);
    }

    @GetMapping("/courses/{id}/students")
    public List<StudentView> getStudentsInCourse(@PathVariable Long id) {
        return service.getStudentsInCourse(id);
    }

    @GetMapping("/students/search")
    public List<StudentView> searchStudents(@RequestParam String keyword) {
        return service.searchStudents(keyword);
    }
}
