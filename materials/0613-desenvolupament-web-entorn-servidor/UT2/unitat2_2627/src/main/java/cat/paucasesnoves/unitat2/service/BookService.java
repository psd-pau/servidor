package cat.paucasesnoves.unitat2.service;

import cat.paucasesnoves.unitat2.domain.entity.Book;
import cat.paucasesnoves.unitat2.domain.enums.Genre;
import cat.paucasesnoves.unitat2.dto.BookPage;
import cat.paucasesnoves.unitat2.exception.ResourceNotFoundException;
import cat.paucasesnoves.unitat2.repository.BookRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository repo;
    private final Random random = new Random();

    public BookService(BookRepository repo) {
        this.repo = repo;
    }

    // CRUD bàsic. La transacció d'escriptura es declara en cada operació.
    public List<Book> getAll() {
        return addSimulatedPrices(repo.findAll(Sort.by("id")));
    }

    public Book getById(Long id) {
        return addSimulatedPrice(findBook(id));
    }

    @Transactional
    public Book create(Book book) {
        // L'ID és del servidor: un POST sempre crea un llibre.
        book.setId(null);
        return addSimulatedPrice(repo.saveAndFlush(book));
    }

    @Transactional
    public Book update(Long id, Book book) {
        Book existing = findBook(id);
        // PUT substitueix tots els camps editables; no és un PATCH parcial.
        existing.setTitle(book.getTitle());
        existing.setAuthor(book.getAuthor());
        existing.setIsbn(book.getIsbn());
        existing.setPublishedDate(book.getPublishedDate());
        existing.setGenre(book.getGenre());
        return addSimulatedPrice(repo.saveAndFlush(existing));
    }

    @Transactional
    public void delete(Long id) {
        repo.delete(findBook(id));
    }

    // Consultes específiques, accessibles des de BookController.
    public Book getByIsbn(String isbn) {
        Book book = repo.findByIsbn(isbn);
        if (book == null) {
            throw new ResourceNotFoundException("No existeix cap llibre amb ISBN " + isbn);
        }
        return addSimulatedPrice(book);
    }

    public List<Book> getByAuthor(String author) {
        return addSimulatedPrices(repo.findByAuthor(author));
    }

    public List<Book> getTop5ByAuthor(String author) {
        return addSimulatedPrices(repo.findByAuthor(author, PageRequest.of(0, 5, Sort.by("id"))).getContent());
    }

    // Ampliació de Page: la resposta publica també totals i número de pàgina.
    public BookPage getPageByAuthor(String author, int page, int size) {
        if (page < 0 || size < 1 || size > 100) {
            throw new IllegalArgumentException("page ha de ser >= 0 i size ha d'estar entre 1 i 100.");
        }
        var result = repo.findByAuthor(author, PageRequest.of(page, size, Sort.by("id")));
        return new BookPage(addSimulatedPrices(result.getContent()), result.getNumber(),
                result.getSize(), result.getTotalElements(), result.getTotalPages());
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

    private Book findBook(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existeix el llibre " + id));
    }

    // Preu de demostració: no es desa, no és estable i no prové d'un servei extern.
    private Book addSimulatedPrice(Book book) {
        BigDecimal price = BigDecimal.valueOf(10 + (90 * random.nextDouble()))
                .setScale(2, RoundingMode.HALF_UP);
        book.setCurrentPrice(price);
        return book;
    }

    private List<Book> addSimulatedPrices(List<Book> books) {
        books.forEach(this::addSimulatedPrice);
        return books;
    }
}
