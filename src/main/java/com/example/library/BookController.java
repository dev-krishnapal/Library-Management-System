package com.example.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable String id) {
        return bookService.getBookById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable String id) {
        bookService.deleteBook(id);
    }
}
