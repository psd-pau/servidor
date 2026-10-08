package cat.paucasesnoves.unitat_2.controller;

import cat.paucasesnoves.unitat_2.domain.entity.Book;
import cat.paucasesnoves.unitat_2.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    //CRUD bàsic

    @GetMapping
    public List<Book> getAll(){
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable Long id){
        return bookService.getById(id);
    }

    @PostMapping
    public Book create(@RequestBody Book book){
        return bookService.create(book);
    }

    @PutMapping("/{id}")
    public Book update(@PathVariable Long id,
                       @RequestBody Book book){
        return bookService.update(id, book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        bookService.delete(id);
    }

}
