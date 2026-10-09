package cat.paucasesnoves.unitat2;

import cat.paucasesnoves.unitat2.repository.TeacherRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Proves amb Hibernate, H2 i MVC reals. No hi ha mocks de repositori.
 * El test no és transaccional: cada petició ha de resoldre les relacions al servei.
 */
@SpringBootTest
@AutoConfigureMockMvc
class CodeAlongIntegrationTests {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper json;
    @Autowired JdbcTemplate jdbc;
    @Autowired DataSource dataSource;
    @Autowired TeacherRepository teachers;
    @Autowired PlatformTransactionManager transactions;

    @BeforeEach
    void resetData() {
        jdbc.update("DELETE FROM ENROLLMENT");
        jdbc.update("DELETE FROM COURSE");
        jdbc.update("DELETE FROM TEACHER");
        jdbc.update("DELETE FROM STUDENT");
        jdbc.update("DELETE FROM BOOK");
        jdbc.execute("ALTER TABLE BOOK ALTER COLUMN ID RESTART WITH 1");
        new ResourceDatabasePopulator(new ClassPathResource("data.sql")).execute(dataSource);
    }

    @Test
    void initialReadsAndQueries() throws Exception {
        mvc.perform(get("/books")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(21));
        mvc.perform(get("/books/1")).andExpect(jsonPath("$.title").value("Clean Code"));
        mvc.perform(get("/books/isbn/9780132350884")).andExpect(jsonPath("$.id").value(1));
        mvc.perform(get("/books/author/{author}", "Robert C. Martin"))
                .andExpect(jsonPath("$.length()").value(3));
        mvc.perform(get("/books/search/title").param("keyword", "Spring"))
                .andExpect(jsonPath("$.length()").value(2));
        mvc.perform(get("/books/genre/POETRY"))
                .andExpect(jsonPath("$.length()").value(2)).andExpect(jsonPath("$[0].title").value("The Waste Land"));
        mvc.perform(get("/books/year/2017")).andExpect(jsonPath("$.length()").value(2));
        mvc.perform(get("/books/count").param("author", "J.K. Rowling").param("genre", "NOVEL"))
                .andExpect(content().string("3"));
        mvc.perform(get("/books/author/Harari/nativeTop5"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void basicCrudAndServerGeneratedId() throws Exception {
        String input = """
                {"id":1,"title":"Prova","author":"David","isbn":"0780130449135",
                 "publishedDate":"2025-01-01","genre":"NOVEL","currentPrice":999}
                """;
        JsonNode created = postJson("/books", input);
        long id = created.get("id").asLong();
        assertThat(id).isEqualTo(22);
        assertThat(created.get("currentPrice").decimalValue()).isBetween(
                new java.math.BigDecimal("10"), new java.math.BigDecimal("100"));
        mvc.perform(get("/books/1")).andExpect(jsonPath("$.title").value("Clean Code"));
        mvc.perform(put("/books/{id}", id).contentType(APPLICATION_JSON)
                .content(bookInput("Prova actualitzada", "David Pons", "0780130449135")))
                .andExpect(status().isOk()).andExpect(jsonPath("$.title").value("Prova actualitzada"));
        mvc.perform(get("/books/{id}", id)).andExpect(jsonPath("$.author").value("David Pons"));
        mvc.perform(delete("/books/{id}", id)).andExpect(status().isNoContent());
        mvc.perform(get("/books/{id}", id)).andExpect(status().isNotFound());
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM BOOK", Integer.class)).isEqualTo(21);
    }

    @Test
    void missingBooksReturn404() throws Exception {
        mvc.perform(get("/books/99999")).andExpect(status().isNotFound());
        mvc.perform(get("/books/isbn/0000000000000")).andExpect(status().isNotFound());
        mvc.perform(put("/books/99999").contentType(APPLICATION_JSON)
                .content(bookInput("Absent", "Autor", "0000000000001"))).andExpect(status().isNotFound());
        mvc.perform(delete("/books/99999")).andExpect(status().isNotFound());
    }

    @Test
    void duplicateIsbnIsConflictAndUpdateRollsBack() throws Exception {
        mvc.perform(put("/books/2").contentType(APPLICATION_JSON)
                .content(bookInput("Canvi que s'ha de desfer", "Robert C. Martin", "9781617294945")))
                .andExpect(status().isConflict());
        mvc.perform(get("/books/2")).andExpect(jsonPath("$.title").value("Clean Architecture"))
                .andExpect(jsonPath("$.isbn").value("9780134494166"));
        mvc.perform(post("/books").contentType(APPLICATION_JSON)
                .content(bookInput("Un altre llibre", "Autor", "9780132350884")))
                .andExpect(status().isConflict());
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM BOOK", Integer.class)).isEqualTo(21);
    }

    @Test
    void titleAndAuthorHaveCompositeUniqueness() throws Exception {
        mvc.perform(post("/books").contentType(APPLICATION_JSON)
                .content(bookInput("Clean Code", "Robert C. Martin", "0000000000010")))
                .andExpect(status().isConflict());
    }

    @Test
    void incompletePutDoesNotEraseFields() throws Exception {
        mvc.perform(put("/books/2").contentType(APPLICATION_JSON).content("{\"isbn\":\"9781617294945\"}"))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.errors.title").exists())
                .andExpect(jsonPath("$.errors.author").exists());
        mvc.perform(get("/books/2")).andExpect(jsonPath("$.title").value("Clean Architecture"));
    }

    @Test
    void invalidBookFieldsAreRejectedBeforePersistence() throws Exception {
        mvc.perform(post("/books").contentType(APPLICATION_JSON)
                .content(bookInput(" ", "Autor", "0000000000011"))).andExpect(status().isBadRequest());
        mvc.perform(post("/books").contentType(APPLICATION_JSON)
                .content(bookInput("a".repeat(151), "Autor", "0000000000012"))).andExpect(status().isBadRequest());
        mvc.perform(post("/books").contentType(APPLICATION_JSON)
                .content(bookInput("Títol", "Autor", "curt"))).andExpect(status().isBadRequest());
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM BOOK", Integer.class)).isEqualTo(21);
    }

    @Test
    void pageAndNativeLimitWorkWithSixBooks() throws Exception {
        for (int i = 4; i <= 6; i++) {
            postJson("/books", bookInput("Demostració " + i, "Robert C. Martin", "000000000010" + i));
        }
        String derived = mvc.perform(get("/books/author/{author}/top5", "Robert C. Martin"))
                .andExpect(jsonPath("$.length()").value(5)).andReturn().getResponse().getContentAsString();
        String nativeQuery = mvc.perform(get("/books/author/{author}/nativeTop5", "Robert C. Martin"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(5))
                .andReturn().getResponse().getContentAsString();
        assertThat(json.readTree(derived).findValues("id")).isEqualTo(json.readTree(nativeQuery).findValues("id"));
        mvc.perform(get("/books/author/{author}/page", "Robert C. Martin").param("page", "0").param("size", "5"))
                .andExpect(jsonPath("$.content.length()").value(5))
                .andExpect(jsonPath("$.totalElements").value(6)).andExpect(jsonPath("$.totalPages").value(2));
        mvc.perform(get("/books/author/{author}/page", "Robert C. Martin").param("page", "1").param("size", "5"))
                .andExpect(jsonPath("$.content.length()").value(1)).andExpect(jsonPath("$.page").value(1));
    }

    @Test
    void invalidPageParametersReturn400() throws Exception {
        mvc.perform(get("/books/author/Autor/page").param("page", "-1")).andExpect(status().isBadRequest());
        mvc.perform(get("/books/author/Autor/page").param("size", "0")).andExpect(status().isBadRequest());
        mvc.perform(get("/books/author/Autor/page").param("size", "101")).andExpect(status().isBadRequest());
    }

    @Test
    void relationshipsSerializeWithoutOpenInViewAndPreserveAliases() throws Exception {
        JsonNode course = createUniversityExample();
        long id = course.get("id").asLong();
        assertThat(course.get("teacher").get("fullName").asText()).isEqualTo("David Pons");
        assertThat(course.get("students").size()).isEqualTo(2);
        assertThat(course.get("teacher").has("courses")).isFalse();
        assertThat(course.get("students").get(0).has("courses")).isFalse();
        mvc.perform(get("/university/courses/teacher/{name}", "David Pons"))
                .andExpect(status().isOk()).andExpect(jsonPath("$[0].students.length()").value(2));
        mvc.perform(get("/api/university/courses/teacher/name/{name}", "David Pons"))
                .andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(id));
        mvc.perform(get("/university/courses/{id}/students", id))
                .andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2));
        mvc.perform(get("/university/students/search").param("keyword", "AINA"))
                .andExpect(jsonPath("$.length()").value(1)).andExpect(jsonPath("$[0].fullName").value("Aina Riera"));
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM ENROLLMENT", Integer.class)).isEqualTo(2);
    }

    @Test
    void missingRelatedIdsDoNotCreateCourses() throws Exception {
        mvc.perform(post("/university/courses").contentType(APPLICATION_JSON)
                .content("{\"name\":\"Curs\",\"credits\":6,\"teacherId\":99999}"))
                .andExpect(status().isNotFound());
        mvc.perform(post("/university/courses").contentType(APPLICATION_JSON)
                .content("{\"name\":\"Curs\",\"credits\":6,\"studentIds\":[99999]}"))
                .andExpect(status().isNotFound());
        mvc.perform(get("/university/courses/99999/students")).andExpect(status().isNotFound());
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM COURSE", Integer.class)).isZero();
    }

    @Test
    void deletingTeacherCascadesToCoursesButPreservesStudents() throws Exception {
        long teacherId = createUniversityExample().get("teacher").get("id").asLong();
        new TransactionTemplate(transactions).executeWithoutResult(status -> teachers.deleteById(teacherId));
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM COURSE", Integer.class)).isZero();
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM ENROLLMENT", Integer.class)).isZero();
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM STUDENT", Integer.class)).isEqualTo(2);
    }

    @Test
    void removingCourseFromTeacherUsesOrphanRemoval() throws Exception {
        long teacherId = createUniversityExample().get("teacher").get("id").asLong();
        new TransactionTemplate(transactions).executeWithoutResult(status -> {
            var teacher = teachers.findById(teacherId).orElseThrow();
            var course = teacher.getCourses().removeFirst();
            course.setTeacher(null);
        });
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM COURSE", Integer.class)).isZero();
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM TEACHER", Integer.class)).isEqualTo(1);
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM STUDENT", Integer.class)).isEqualTo(2);
    }

    private JsonNode createUniversityExample() throws Exception {
        long teacher = postJson("/university/teachers", "{\"fullName\":\"David Pons\",\"department\":\"Informàtica\"}").get("id").asLong();
        long aina = postJson("/university/students", "{\"fullName\":\"Aina Riera\",\"email\":\"aina@example.test\"}").get("id").asLong();
        long marc = postJson("/university/students", "{\"fullName\":\"Marc Vidal\",\"email\":\"marc@example.test\"}").get("id").asLong();
        // Repetir un ID no ha de duplicar la matrícula.
        return postJson("/university/courses", """
                {"name":"Servidor","credits":6,"teacherId":%d,"studentIds":[%d,%d,%d]}
                """.formatted(teacher, aina, marc, aina));
    }

    private JsonNode postJson(String path, String body) throws Exception {
        String response = mvc.perform(post(path).contentType(APPLICATION_JSON).content(body))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString(java.nio.charset.StandardCharsets.UTF_8);
        return json.readTree(response);
    }

    private String bookInput(String title, String author, String isbn) {
        return json.createObjectNode().put("title", title).put("author", author).put("isbn", isbn)
                .put("genre", "SCIENCE").toString();
    }
}
