package cat.paucasesnoves.unitat2.repository;


import cat.paucasesnoves.unitat2.domain.entity.Book;
import cat.paucasesnoves.unitat2.domain.enums.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository de Book
 *
 * Extén JpaRepository → Spring Data JPA genera automàticament:
 *   - findAll()
 *   - findById(ID id)
 *   - save(T entity)
 *   - deleteById(ID id)
 *   - delete(T entity)
 *   - count()
 *   - existsById(ID id)
 *   - findAll(Sort sort)
 *   - findAll(Pageable pageable)
 *
 * ➝ Aquests mètodes NO cal declarar-los.
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    // =========================================================
    // 🔹 Mètodes derivats del NOM (Spring els implementa sol)
    // =========================================================

    /**
     * Cerca un llibre pel seu ISBN (camp únic).
     * SQL generat:
     *   SELECT * FROM books WHERE isbn = ?;
     */
    Book findByIsbn(String isbn);

    /**
     * Llista tots els llibres d’un autor concret.
     * SQL generat:
     *   SELECT * FROM books WHERE author = ?;
     */
    List<Book> findByAuthor(String author);

    /**
     * Cerca llibres d’un autor amb suport de paginació.
     * Exemple: top 5, pàgina concreta...
     * SQL generat (MySQL/H2):
     *   SELECT * FROM books WHERE author = ? LIMIT ? OFFSET ?;
     */
    Page<Book> findByAuthor(String author, Pageable pageable);


    /**
     * Cerca llibres amb un fragment al títol (LIKE).
     * SQL generat:
     *   SELECT * FROM books WHERE title LIKE %?%;
     */
    List<Book> findByTitleContaining(String keyword);

    /**
     * Llibres d’un gènere ordenats per data de publicació descendent.
     * SQL generat:
     *   SELECT * FROM books WHERE genre = ? ORDER BY published_date DESC;
     */
    List<Book> findByGenreOrderByPublishedDateDesc(Genre genre);

    // =========================================================
    // 🔹 Mètodes complexos (necessiten @Query)
    // =========================================================

    /**
     * Llibres publicats en un any concret.
     * JPQL:
     *   SELECT b FROM Book b WHERE YEAR(b.publishedDate) = :year
     * SQL (MySQL):
     *   SELECT * FROM books b WHERE YEAR(b.published_date) = ?;
     */
    @Query("SELECT b FROM Book b WHERE YEAR(b.publishedDate) = :year")
    List<Book> findBooksPublishedInYear(@Param("year") int year);

    /**
     *
     * Comptar quants llibres té un autor d’un gènere concret.
     * JPQL:
     *   SELECT COUNT(b) FROM Book b
     *   WHERE b.author = :author AND b.genre = :genre
     * SQL (MySQL):
     *   SELECT COUNT(*) FROM books WHERE author = ? AND genre = ?;
     */
    @Query("SELECT COUNT(b) FROM Book b WHERE b.author = :author AND b.genre = :genre")
    long countBooksByAuthorAndGenre(@Param("author") String author, @Param("genre") Genre genre);

    /**
     * Exemple amb SQL natiu (no JPQL). JPQL No suporta limit
     * Native Query:
     *   SELECT * FROM books WHERE author LIKE %:name% LIMIT 5;
     */
    @Query(value = "SELECT * FROM books WHERE author LIKE %:name% LIMIT 5", nativeQuery = true)
    List<Book> searchTop5ByAuthor(@Param("name") String name);
}
