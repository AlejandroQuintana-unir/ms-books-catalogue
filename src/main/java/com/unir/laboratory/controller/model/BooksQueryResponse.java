package com.unir.laboratory.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import com.unir.laboratory.entity.ElasticBook;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BooksQueryResponse {

    private List<ElasticBook> books;
    private List<AggregationDetails> aggs;

}
