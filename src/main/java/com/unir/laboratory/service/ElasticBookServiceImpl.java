package com.unir.laboratory.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.unir.laboratory.controller.model.BooksQueryResponse;
import com.unir.laboratory.controller.model.CreateBookRequest;
import com.unir.laboratory.entity.ElasticBook;
import com.unir.laboratory.repository.DataAccessRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ElasticBookServiceImpl implements ElasticBookService {

    private final DataAccessRepository repository;

    @Override
    public BooksQueryResponse getBooks(String title, String description, String genre, String author, String publisher,
                                       String isbnCode, Double priceIvaMin, Double priceIvaMax,
                                       Double priceDigitalIvaMin, Double priceDigitalIvaMax,
                                       Integer valoration, Boolean aggregate) {
        // Ahora por defecto solo devolverá libros visibles
        return repository.findBooks(title, description, genre, author, publisher, isbnCode,
                                    priceIvaMin, priceIvaMax, priceDigitalIvaMin, priceDigitalIvaMax, valoration, aggregate);
    }

    @Override
    public ElasticBook getBook(String bookId) {
        return repository.findById(bookId).orElse(null);
    }

    @Override
    public Boolean removeBook(String bookId) {
        Optional<ElasticBook> book = repository.findById(bookId);
        if (book.isPresent()) {
            repository.delete(book.get());
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public ElasticBook createBook(CreateBookRequest request) {
        if (request != null &&
                StringUtils.hasLength(request.getTitle().trim()) &&
                StringUtils.hasLength(request.getAuthor().trim()) &&
                StringUtils.hasLength(request.getGenre().trim()) &&
                request.getVisibility() != null) {

            ElasticBook book = ElasticBook.builder()
                    .id(UUID.randomUUID().toString())
                    .title(request.getTitle())
                    .author(request.getAuthor())
                    .edition(request.getEdition())
                    .publisher(request.getPublisher())
                    .publishDate(request.getPublishDate())
                    .isbnCode(request.getIsbnCode())
                    .price(request.getPrice())
                    .priceIva(request.getPriceIva())
                    .priceDigital(request.getPriceDigital())
                    .priceDigitalIva(request.getPriceDigitalIva())
                    .genre(request.getGenre())
                    .description(request.getDescription())
                    .stock(request.getStock())
                    .valoration(request.getValoration())
                    .visibility(request.getVisibility())
                    .build();

            return repository.save(book);
        } else {
            return null;
        }
    }
}