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
 * JpaRepository<Book, Long> ja proporciona findAll, findById, save, delete,
 * count, existsById i les variants amb Sort i Pageable.
 * Els mètodes següents afegeixen consultes específiques del nostre domini.
 */
public interface BookRepository extends JpaRepository<Book, Long> {
    // Cerca per una columna única: SQL equivalent WHERE isbn = ?
    Book findByIsbn(String isbn);

    // Autor exacte; no és una cerca per fragment.
    List<Book> findByAuthor(String author);

    // Ampliació: Page conté els resultats i les metadades de paginació.
    Page<Book> findByAuthor(String author, Pageable pageable);

    // Containing prepara el patró LIKE amb el fragment rebut.
    List<Book> findByTitleContaining(String keyword);

    List<Book> findByGenreOrderByPublishedDateDesc(Genre genre);

    // JPQL usa l'entitat Book i el camp Java publishedDate.
    @Query("SELECT b FROM Book b WHERE YEAR(b.publishedDate) = :year")
    List<Book> findBooksPublishedInYear(@Param("year") int year);

    @Query("SELECT COUNT(b) FROM Book b WHERE b.author = :author AND b.genre = :genre")
    long countBooksByAuthorAndGenre(@Param("author") String author, @Param("genre") Genre genre);

    // SQL natiu usa la taula BOOK. CONCAT forma el patró; el paràmetre es vincula.
    // ORDER BY defineix quins són els cinc primers. JPQL també es pot limitar amb Pageable.
    @Query(value = "SELECT * FROM BOOK WHERE author LIKE CONCAT('%', :name, '%') ORDER BY id LIMIT 5",
            nativeQuery = true)
    List<Book> searchTop5ByAuthor(@Param("name") String name);
}
