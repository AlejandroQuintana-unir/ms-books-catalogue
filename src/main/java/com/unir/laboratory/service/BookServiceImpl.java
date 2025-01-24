package com.unir.laboratory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unir.laboratory.entity.Book;
import com.unir.laboratory.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public void createBook(Book book) {
        try{
            bookRepository.save(book);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
}
