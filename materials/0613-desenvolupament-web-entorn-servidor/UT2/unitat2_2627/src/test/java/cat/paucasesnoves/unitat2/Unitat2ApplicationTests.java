package cat.paucasesnoves.unitat2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import cat.paucasesnoves.unitat2.repository.BookRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class Unitat2ApplicationTests {

	@Autowired BookRepository books;
	@Autowired JdbcTemplate jdbc;

	@Test
	void contextLoads() {
		// Comprova la inicialització real d'arrencada, sense preparar dades al test.
		assertThat(books.count()).isEqualTo(21);
		assertThat(books.findByIsbn("9780132350884").getTitle()).isEqualTo("Clean Code");
		assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS "
				+ "WHERE TABLE_NAME = 'BOOK' AND COLUMN_NAME = 'CURRENT_PRICE'", Integer.class)).isZero();
	}

}
