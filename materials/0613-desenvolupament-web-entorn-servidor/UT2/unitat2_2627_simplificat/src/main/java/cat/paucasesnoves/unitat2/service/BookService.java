package cat.paucasesnoves.unitat2.service;

import cat.paucasesnoves.unitat2.domain.entity.Book;
import cat.paucasesnoves.unitat2.domain.enums.Genre;
import cat.paucasesnoves.unitat2.repository.BookRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

@Service
public class BookService {
    private final BookRepository repo;
    private final Random random = new Random();

    public BookService(BookRepository repo) {
        this.repo = repo;
    }

    // CRUD: els mètodes de JpaRepository gestionen l'accés a la BD.
    public List<Book> getAll() {
        return addSimulatedPrices(repo.findAll(Sort.by("id")));
    }

    public Book getById(Long id) {
        return repo.findById(id).map(this::addSimulatedPrice).orElse(null);
    }

    public Book create(Book book) {
        book.setId(null); // La BD genera l'identificador del llibre nou.
        return repo.save(book);
    }

    public Book update(Long id, Book book) {
        return repo.findById(id).map(existing -> {
            existing.setTitle(book.getTitle());
            existing.setAuthor(book.getAuthor());
            existing.setIsbn(book.getIsbn());
            existing.setPublishedDate(book.getPublishedDate());
            existing.setGenre(book.getGenre());
            return repo.save(existing);
        }).orElse(null);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    // Consultes: el servei només crida el repositori i afegeix el preu simulat.
    public Book getByIsbn(String isbn) {
        return addSimulatedPrice(repo.findByIsbn(isbn));
    }

    public List<Book> getByAuthor(String author) {
        return addSimulatedPrices(repo.findByAuthor(author));
    }

    public List<Book> getTop5ByAuthor(String author) {
        return getPageByAuthor(author, 0, 5);
    }

    public List<Book> getPageByAuthor(String author, int page, int size) {
        var result = repo.findByAuthor(author, PageRequest.of(page, size, Sort.by("id")));
        // Page continua al repositori; per HTTP només mostram els llibres de la pàgina.
        return addSimulatedPrices(result.getContent());
    }

    public List<Book> searchByTitle(String keyword) {
        return addSimulatedPrices(repo.findByTitleContaining(keyword));
    }

    public List<Book> getByGenreOrdered(Genre genre) {
        return addSimulatedPrices(repo.findByGenreOrderByPublishedDateDesc(genre));
    }

    public List<Book> getBooksInYear(int year) {
        return addSimulatedPrices(repo.findBooksPublishedInYear(year));
    }

    public long countBooksByAuthorAndGenre(String author, Genre genre) {
        return repo.countBooksByAuthorAndGenre(author, genre);
    }

    public List<Book> searchTop5ByAuthorNative(String name) {
        return addSimulatedPrices(repo.searchTop5ByAuthor(name));
    }

    // Demostració de @Transient: preu calculat que no té columna a BOOK.
    private Book addSimulatedPrice(Book book) {
        if (book != null) {
            BigDecimal price = BigDecimal.valueOf(10 + (90 * random.nextDouble()))
                    .setScale(2, RoundingMode.HALF_UP);
            book.setCurrentPrice(price);
        }
        return book;
    }

    private List<Book> addSimulatedPrices(List<Book> books) {
        books.forEach(this::addSimulatedPrice);
        return books;
    }
}
