package cat.paucasesnoves.unitat2;

import cat.paucasesnoves.unitat2.domain.entity.*;
import cat.paucasesnoves.unitat2.domain.enums.Genre;
import cat.paucasesnoves.unitat2.repository.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

/** Proves de persistència amb H2 real; cada cas es desfà en acabar. */
@SpringBootTest
@Transactional
class PersistenciaTests {
    @Autowired BookRepository books;
    @Autowired TeacherRepository teachers;
    @Autowired CourseRepository courses;
    @Autowired StudentRepository students;
    @Autowired EntityManager entities;
    @Autowired JdbcTemplate jdbc;

    @Test
    void dadesInicialsIMapatge() {
        assertThat(books.count()).isEqualTo(21);
        Book book = books.findByIsbn("9780132350884");
        assertThat(book.getTitle()).isEqualTo("Clean Code");
        assertThat(book.getGenre()).isEqualTo(Genre.SCIENCE);
        assertThat(jdbc.queryForObject("SELECT genre FROM BOOK WHERE id = ?", String.class, book.getId()))
                .isEqualTo("SCIENCE");
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS "
                + "WHERE TABLE_NAME = 'BOOK' AND COLUMN_NAME = 'CURRENT_PRICE'", Integer.class)).isZero();
        book.setCurrentPrice(new BigDecimal("25.50"));
        books.saveAndFlush(book);
        entities.clear();
        assertThat(books.findById(book.getId()).orElseThrow().getCurrentPrice()).isNull();
    }

    @Test
    void crudAmbIdentificadorGenerat() {
        Book book = new Book("Prova", "David", "0000000000001", LocalDate.of(2026, 10, 9), Genre.NOVEL);
        assertThat(book.getId()).isNull();
        books.saveAndFlush(book);
        Long id = book.getId();
        assertThat(id).isNotNull();
        book.setTitle("Prova actualitzada");
        books.saveAndFlush(book);
        entities.clear();
        assertThat(books.findById(id).orElseThrow().getTitle()).isEqualTo("Prova actualitzada");
        books.deleteById(id);
        books.flush();
        assertThat(books.findById(id)).isEmpty();
        assertThat(books.count()).isEqualTo(21);
    }

    @Test
    void consultesDerivadesJpqlINatives() {
        assertThat(books.findByAuthor("Robert C. Martin")).hasSize(3);
        assertThat(books.findByTitleContaining("Spring")).hasSize(2);
        assertThat(books.findByGenreOrderByPublishedDateDesc(Genre.POETRY))
                .extracting(Book::getTitle).containsExactly("The Waste Land", "Leaves of Grass");
        assertThat(books.findBooksPublishedInYear(2017)).hasSize(2);
        assertThat(books.countBooksByAuthorAndGenre("J.K. Rowling", Genre.NOVEL)).isEqualTo(3);
        assertThat(books.searchTop5ByAuthor("Harari")).hasSize(2);
    }

    @Test
    void paginacioILimitAmbSisLlibres() {
        for (int i = 4; i <= 6; i++) {
            books.save(new Book("Demostració " + i, "Robert C. Martin", "000000000010" + i, null, Genre.SCIENCE));
        }
        books.flush();
        var first = books.findByAuthor("Robert C. Martin", PageRequest.of(0, 5, Sort.by("id")));
        var second = books.findByAuthor("Robert C. Martin", PageRequest.of(1, 5, Sort.by("id")));
        assertThat(first.getContent()).hasSize(5);
        assertThat(first.getTotalElements()).isEqualTo(6);
        assertThat(first.getTotalPages()).isEqualTo(2);
        assertThat(second.getContent()).hasSize(1);
        assertThat(books.searchTop5ByAuthor("Robert C. Martin")).extracting(Book::getId)
                .containsExactlyElementsOf(first.getContent().stream().map(Book::getId).toList());
    }

    @Test
    void isbnHaDeSerUnic() {
        Book duplicate = new Book("Un altre títol", "Un altre autor", "9780132350884", null, Genre.SCIENCE);
        assertThatThrownBy(() -> books.saveAndFlush(duplicate)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void titolIAutorHanDeSerUnicsEnConjunt() {
        Book duplicate = new Book("Clean Code", "Robert C. Martin", "0000000000002", null, Genre.SCIENCE);
        assertThatThrownBy(() -> books.saveAndFlush(duplicate)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void columnaObligatoriaNoAdmetNull() {
        Book incomplete = new Book(null, "Autor", "0000000000003", null, Genre.NOVEL);
        assertThatThrownBy(() -> books.saveAndFlush(incomplete)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void costatPropietariDesaLesRelacions() {
        Course course = exempleUniversitat();
        Long teacherId = course.getTeacher().getId();
        Long studentId = course.getStudents().getFirst().getId();
        entities.clear(); // Llegim també els costats inversos des de la BD.
        assertThat(teachers.findById(teacherId).orElseThrow().getCourses()).hasSize(1);
        assertThat(students.findById(studentId).orElseThrow().getCourses()).hasSize(1);
        assertThat(courses.findByTeacherFullName("David Pons")).hasSize(1);
        assertThat(courses.findByCreditsGreaterThan(5)).hasSize(1);
        assertThat(teachers.findByDepartment("Informàtica")).hasSize(1);
        assertThat(students.findByFullNameContainingIgnoreCase("AINA")).hasSize(1);
        assertThat(jdbc.queryForObject("SELECT teacher_id FROM COURSE WHERE id = ?", Long.class, course.getId()))
                .isEqualTo(teacherId);
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM ENROLLMENT", Integer.class)).isEqualTo(1);
    }

    @Test
    void cascadaEliminaCursosIMatriculesPeroConservaEstudiants() {
        Long teacherId = exempleUniversitat().getTeacher().getId();
        entities.clear();
        teachers.deleteById(teacherId);
        teachers.flush();
        assertThat(courses.count()).isZero();
        assertThat(students.count()).isEqualTo(1);
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM ENROLLMENT", Integer.class)).isZero();
    }

    @Test
    void orphanRemovalEliminaElCursRetiratDeLaColleccio() {
        Long teacherId = exempleUniversitat().getTeacher().getId();
        entities.clear();
        Teacher teacher = teachers.findById(teacherId).orElseThrow();
        teacher.getCourses().clear();
        teachers.flush();
        assertThat(courses.count()).isZero();
        assertThat(teachers.existsById(teacherId)).isTrue();
        assertThat(students.count()).isEqualTo(1);
    }

    private Course exempleUniversitat() {
        Teacher teacher = new Teacher();
        teacher.setFullName("David Pons");
        teacher.setDepartment("Informàtica");
        teachers.save(teacher);
        Student student = new Student();
        student.setFullName("Aina Riera");
        student.setEmail("aina@example.test");
        students.save(student);
        Course course = new Course();
        course.setName("Servidor");
        course.setCredits(6);
        course.setTeacher(teacher);
        course.getStudents().add(student);
        return courses.saveAndFlush(course);
    }
}
