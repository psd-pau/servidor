package cat.paucasesnoves.unitat2.controller;

import cat.paucasesnoves.unitat2.domain.entity.Book;
import cat.paucasesnoves.unitat2.domain.enums.Genre;
import cat.paucasesnoves.unitat2.dto.BookPage;
import cat.paucasesnoves.unitat2.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    // =========================================================
    //  CRUD bàsic
    // =========================================================

    @GetMapping
    public List<Book> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@Valid @RequestBody Book book) {
        return service.create(book);
    }

    @PutMapping("/{id}")
    public Book update(@PathVariable Long id, @Valid @RequestBody Book book) {
        return service.update(id, book);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // =========================================================
    //  Consultes específiques
    // =========================================================

    @GetMapping("/isbn/{isbn}")
    public Book getByIsbn(@PathVariable String isbn) {
        return service.getByIsbn(isbn);
    }

    @GetMapping("/author/{author}")
    public List<Book> getByAuthor(@PathVariable String author) {
        return service.getByAuthor(author);
    }

    @GetMapping("/author/{author}/top5")
    public List<Book> getTop5ByAuthor(@PathVariable String author) {
        return service.getTop5ByAuthor(author);
    }

    // Ampliació: conservar les metadades de Page a la resposta.
    @GetMapping("/author/{author}/page")
    public BookPage getPageByAuthor(@PathVariable String author,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "5") int size) {
        return service.getPageByAuthor(author, page, size);
    }

    @GetMapping("/search/title")
    public List<Book> searchByTitle(@RequestParam String keyword) {
        return service.searchByTitle(keyword);
    }

    @GetMapping("/genre/{genre}")
    public List<Book> getByGenre(@PathVariable Genre genre) {
        return service.getByGenreOrdered(genre);
    }

    @GetMapping("/year/{year}")
    public List<Book> getBooksInYear(@PathVariable int year) {
        return service.getBooksInYear(year);
    }

    @GetMapping("/count")
    public long countByAuthorAndGenre(@RequestParam String author, @RequestParam Genre genre) {
        return service.countBooksByAuthorAndGenre(author, genre);
    }

    @GetMapping("/author/{author}/nativeTop5")
    public List<Book> searchTop5ByAuthorNative(@PathVariable String author) {
        return service.searchTop5ByAuthorNative(author);
    }
}
