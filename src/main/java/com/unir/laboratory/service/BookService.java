package com.unir.laboratory.service;

import java.util.List;

import com.unir.laboratory.entity.Book;

public interface BookService {
    
    List<Book> getAllBooks();

    void createBook(Book book);
} 
