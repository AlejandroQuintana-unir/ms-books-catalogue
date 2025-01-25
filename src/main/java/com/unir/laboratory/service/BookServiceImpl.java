package com.unir.laboratory.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.unir.laboratory.entity.Book;
import com.unir.laboratory.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    public List<Book> getBooks(String author, String title, Date publishDate, String isbnCode, String genre,
            Integer valoration, Boolean visibility) {

        if (StringUtils.hasText(author) || StringUtils.hasText(title) || publishDate != null
                || StringUtils.hasText(isbnCode) || StringUtils.hasText(genre) || valoration != null
                || visibility != null) {
            return bookRepository.search(author, title, publishDate, isbnCode, genre, valoration, visibility);
        }
        List<Book> books = bookRepository.getBooks();
        return books.isEmpty() ? null : books;

    }

    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

}
