package cat.paucasesnoves.unitat2.service;

import cat.paucasesnoves.unitat2.domain.entity.Book;
import cat.paucasesnoves.unitat2.domain.enums.Genre;
import cat.paucasesnoves.unitat2.repository.BookRepository;
import org.springframework.data.domain.PageRequest;
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

    // =========================================================
    //  CRUD bàsic
    // =========================================================
    public List<Book> getAll() {
        return addSimulatedPrices(repo.findAll());
    }

    public Book getById(Long id) {
        return repo.findById(id)
                .map(this::addSimulatedPrice)
                .orElse(null);
    }

    public Book create(Book book) {
        return repo.save(book);
    }


    public Book update(Long id, Book book) {
        return repo.findById(id).map(b -> {
            b.setTitle(book.getTitle());
            b.setAuthor(book.getAuthor());
            b.setIsbn(book.getIsbn());
            b.setPublishedDate(book.getPublishedDate());
            b.setGenre(book.getGenre());
            return repo.save(b);
        }).orElse(null);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    // =========================================================
    // 🔹 Consultes específiques
    // =========================================================
    public Book getByIsbn(String isbn) {
        return addSimulatedPrice(repo.findByIsbn(isbn));
    }

    public List<Book> getByAuthor(String author) {
        return addSimulatedPrices(repo.findByAuthor(author));
    }

    // Nova versió amb Pageable → top 5
    public List<Book> getTop5ByAuthor(String author) {
        return addSimulatedPrices(
                repo.findByAuthor(author, PageRequest.of(0, 5)).getContent()
        );
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

    // =========================================================
    // 🔹 Afegir el camp simulat currentPrice
    // =========================================================
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
