package com.unir.laboratory.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unir.laboratory.controller.model.BooksQueryResponse;
import com.unir.laboratory.controller.model.CreateBookRequest;
import com.unir.laboratory.entity.ElasticBook;
import com.unir.laboratory.service.ElasticBookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/books")
public class ElasticBookController {

    private final ElasticBookService service;

    @GetMapping
    public ResponseEntity<BooksQueryResponse> getBooks(
            @RequestHeader Map<String, String> headers,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String publisher,
            @RequestParam(required = false) String isbnCode,
            @RequestParam(required = false) Double priceIvaMin,
            @RequestParam(required = false) Double priceIvaMax,
            @RequestParam(required = false) Double priceDigitalIvaMin,
            @RequestParam(required = false) Double priceDigitalIvaMax,
            @RequestParam(required = false) Integer valoration,
            @RequestParam(required = false, defaultValue = "false") Boolean aggregate) {

        log.info("Headers: {}", headers);
        BooksQueryResponse books = service.getBooks(title, description, genre, author, publisher, isbnCode,
                priceIvaMin, priceIvaMax, priceDigitalIvaMin, priceDigitalIvaMax, valoration, aggregate);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<ElasticBook> getBook(@PathVariable String bookId) {
        log.info("Request received for book {}", bookId);
        ElasticBook book = service.getBook(bookId);

        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable String bookId) {
        Boolean removed = service.removeBook(bookId);

        if (Boolean.TRUE.equals(removed)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ElasticBook> createBook(@RequestBody CreateBookRequest request) {
        ElasticBook createdBook = service.createBook(request);

        if (createdBook != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}