package com.unir.laboratory.service;

import java.sql.Date;
import java.util.List;

import com.unir.laboratory.entity.Book;

public interface BookService {

    List<Book> getBooks(String author, String title, Date publishDate, String isbnCode, String genre,
            Integer valoration, Boolean visibility);

    Book createBook(Book book);
}
