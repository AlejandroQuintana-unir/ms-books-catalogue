package com.unir.laboratory.repository.utils;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class SearchCriteria<Book> implements Specification<Book> {

    private final List<SearchStatement> list = new LinkedList<>();

    public void add(SearchStatement criteria) {
        list.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        List<Predicate> predicates = new LinkedList<>();

        for (SearchStatement criteria : list) {
            if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                predicates.add(builder.equal(
                        root.get(criteria.getKey()), criteria.getValue()));
            }

        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }

}
