package cat.paucasesnoves.unitat_2.repository;


/*
    Repository de Book
    Extenem l'interface JPARepository
    Genera automàticament:
     - findAll() -> select * from ...
     - findById(ID id) -> select * from... where id = ...
     - save (T entity) -> Insert/Update
     - delete(T entity) -> Delete
     - deleteById(ID id) -> Delete
     - count() -> select count(*) from...
     - existsById(ID id) ->
     - findAll(Sort sort) -> select * from ... order by "sort"
     - findAll(Pageable pageable) -> select * from ... limit 30, 39
 */

import cat.paucasesnoves.unitat_2.domain.entity.Book;
import cat.paucasesnoves.unitat_2.domain.enums.Genre;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// JpaRepository<T Entity, ID id>
public interface BookRepository extends JpaRepository<Book, Long> {
    /*
    Mètodes derivats del NOM (Spring els implementa totsol)
     */
    // select * from BOOK where isbn = ?;
    Book findByIsbn(String isbn);

    // select * from BOOK where author = ?;
    List<Book> findByAuthor(String author);

    //select * from BOOK where author = ? LIMIT inici_pagina, fi_pagina;
    //select * from BOOK where author = ? LIMIT ?, OFFSET mida_pagina;
    List<Book> findByAuthor(String author, Pageable pageable);

    //Select * from BOOK where title like '%keyword%';
    List<Book> findByTitleContaining(String keyword);

    //select * from BOOK where genre = ? Order by published_date desc
    List<Book> findByGenreOrderByPublishedDateDesc(Genre genre);

    /*
        Complex queries
     */
    @Query("select b from Book b where YEAR(b.publishedDate) = :year")
    List<Book> findBooksByPublishedInYear(@Param("year") int year);

    @Query("select count(b) from Book b where b.author = :author and b.genre = :genre")
    long countBooksByAuthorAndGenre(@Param("author") String author,
                                    @Param("genre") Genre genre);


    /*
        Llenguatge JPQL (variant de Spring JPA de SQL)
        JPQL no te limit, hem de fer una query nativa.
        Si hi ha funcionalitats no incloses a JPQL que requerim,
        cal fer una nativeQuery
     */
    @Query(value = "select * from BOOK where author like '%:author%' limit 5", nativeQuery = true)
    List<Book> searchTop5ByAuthor(@Param("author") String author);
}
