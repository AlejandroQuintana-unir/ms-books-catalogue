package com.unir.laboratory.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.unir.laboratory.entity.Book;
import com.unir.laboratory.service.BookService;

@Controller
public class BookController {

    @Autowired 
    BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getBooks(String author, String title, Date publishDate, String isbnCode,
            String genre, Integer valoration, Boolean visibility) {

        List<Book> books = bookService.getBooks(author, title, publishDate, isbnCode, genre, valoration, visibility);

        if (books != null) {
            return ResponseEntity.ok(books);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("/books/{bookId}")
    public ResponseEntity<Book> getBook(@PathVariable String bookId) {
        Book book = bookService.getBook(bookId);

        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book CreatedBook = bookService.createBook(book);

        if (CreatedBook != null) {
            return ResponseEntity.created(null).body(CreatedBook);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable String bookId) {
        Boolean book = bookService.removeBook(bookId);

        if (Boolean.TRUE.equals(book)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/books/{bookId}")
    public ResponseEntity<Book> patchBook(@PathVariable String bookId, @RequestBody Book book) {
        Book UpdatedBook = bookService.patchBook(bookId, book);

        if (UpdatedBook != null) {
            return ResponseEntity.ok(UpdatedBook);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/books/{bookId}")
    public ResponseEntity<Book> putBook(@PathVariable String bookId, @RequestBody Book book) {
        Book UpdatedBook = bookService.updateBook(bookId, book);

        if (UpdatedBook != null) {
            return ResponseEntity.ok(UpdatedBook);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
