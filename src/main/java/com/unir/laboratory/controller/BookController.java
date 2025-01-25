package com.unir.laboratory.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import com.unir.laboratory.entity.Book;
import com.unir.laboratory.service.BookService;

@Controller
public class BookController {

    @Autowired // Es neceario el autowired para que funcione la inyección de dependencias?
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

    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book CreatedBook = bookService.createBook(book);

        if (CreatedBook != null) {
            return ResponseEntity.created(null).body(CreatedBook);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
