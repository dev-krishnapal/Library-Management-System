package com.example.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }

    public void borrowBook(String id) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            if (book.isAvailable()) {
                book.setAvailable(false);
                bookRepository.save(book);
            }
        }
    }

    public void returnBook(String id) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            if (!book.isAvailable()) {
                book.setAvailable(true);
                bookRepository.save(book);
            }
        }
    }
}
