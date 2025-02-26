package com.unir.laboratory.service;

import com.unir.laboratory.controller.model.BooksQueryResponse;
import com.unir.laboratory.controller.model.CreateBookRequest;
import com.unir.laboratory.entity.ElasticBook;

public interface ElasticBookService {

    public BooksQueryResponse getBooks(String title, String description, String genre, String author, String publisher,
                                       String isbnCode, Double priceIvaMin, Double priceIvaMax,
                                       Double priceDigitalIvaMin, Double priceDigitalIvaMax,
                                       Integer valoration, Boolean aggregate);

    ElasticBook getBook(String bookId);

    Boolean removeBook(String bookId);

    ElasticBook createBook(CreateBookRequest request);
}