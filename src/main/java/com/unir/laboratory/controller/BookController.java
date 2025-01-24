package com.unir.laboratory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import com.unir.laboratory.entity.Book;
import com.unir.laboratory.service.BookService;

@Controller
public class BookController {

    @Autowired
    BookService bookService;
    
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getMethodName() {
        return new ResponseEntity<List<Book>>(bookService.getAllBooks(), HttpStatusCode.valueOf(200));
    }

    @PostMapping("/books")
    public ResponseEntity<String> postMethodName(@RequestBody Book book) {
        try {
            bookService.createBook(book);
            return ResponseEntity.ok("Creado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    

}
