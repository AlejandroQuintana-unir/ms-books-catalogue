package com.unir.laboratory.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.unir.laboratory.entity.Book;
import com.unir.laboratory.repository.utils.SearchCriteria;
import com.unir.laboratory.repository.utils.SearchOperation;
import com.unir.laboratory.repository.utils.SearchStatement;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final BookJpaRepository repository;

    public List<Book> getBooks() {
        return repository.findAll();
    }

    public Book save(Book book) {
        return repository.save(book);
    }

    public List<Book> search(String author, String title, Date publishDate, String isbnCode, String genre,
            Integer valoration, Boolean visibility) {

        SearchCriteria<Book> spec = new SearchCriteria<>();

        if (valoration != null) {
            spec.add(new SearchStatement("valoration", valoration, SearchOperation.EQUAL));
        }

        return repository.findAll(spec);
    }

}
