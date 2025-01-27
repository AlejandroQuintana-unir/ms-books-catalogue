package com.unir.laboratory.service;

import java.sql.Date;
import java.util.List;

import com.unir.laboratory.entity.Book;

public interface BookService {

    List<Book> getBooks(String author, String title, Date publishDate, String isbnCode, String genre,
            Integer valoration, Boolean visibility);

    Book getBook(String bookId);

    Book createBook(Book book);

    Boolean removeBook(String bookId);

    Book PatchBook(String bookId, Book book);

    Book updateBook(String bookId, Book book);

}
