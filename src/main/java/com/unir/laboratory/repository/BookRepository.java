package com.unir.laboratory.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.unir.laboratory.entity.Book;
import com.unir.laboratory.repository.utils.SearchCriteria;
import com.unir.laboratory.repository.utils.SearchOperation;
import com.unir.laboratory.repository.utils.SearchStatement;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final BookJpaRepository repository;

    public List<Book> getBooks() {
        return repository.findAll();
    }

    public Book getById(String id) {
        return repository.findById(Long.valueOf(id)).orElse(null);
    }

    public Book save(Book book) {
        return repository.save(book);
    }

    public void delete(Book book) {
        repository.delete(book);
    }

    public List<Book> search(String author, String title, Date publishDate, String isbnCode, String genre,
            Integer valoration, Boolean visibility) {

        SearchCriteria<Book> spec = new SearchCriteria<>();

        if (valoration != null) {
            spec.add(new SearchStatement("valoration", valoration, SearchOperation.EQUAL));
        }

        if (visibility != null) {
            spec.add(new SearchStatement("visibility", visibility, SearchOperation.EQUAL));
        }

        if (StringUtils.isNotBlank(author)) {
            spec.add(new SearchStatement("author", author, SearchOperation.MATCH));
        }

        if (StringUtils.isNotBlank(title)) {
            spec.add(new SearchStatement("title", title, SearchOperation.MATCH));
        }

        if (StringUtils.isNotBlank(isbnCode)) {
            spec.add(new SearchStatement("isbnCode", isbnCode, SearchOperation.EQUAL));
        }

        if (StringUtils.isNotBlank(genre)) {
            spec.add(new SearchStatement("genre", genre, SearchOperation.EQUAL));
        }

        if (valoration != null) {
            spec.add(new SearchStatement("valoration", valoration, SearchOperation.GREATER_THAN_EQUAL));
        }

        if (publishDate != null) {
            spec.add(new SearchStatement("publishDate", publishDate, SearchOperation.EQUAL));
        }

        return repository.findAll(spec);
    }

}
