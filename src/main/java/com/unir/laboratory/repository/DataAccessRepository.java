package com.unir.laboratory.repository;

import java.util.*;

import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder.Type;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

import com.unir.laboratory.controller.model.AggregationDetails;
import com.unir.laboratory.controller.model.BooksQueryResponse;
import com.unir.laboratory.entity.ElasticBook;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class DataAccessRepository {

    @Value("${server.fullAddress}")
    private String serverFullAddress;

    private final ElasticBookRepository bookRepository;
    private final ElasticsearchOperations elasticClient;

    private final String[] descriptionSearchFields = { "description", "description.search" };

    public ElasticBook save(ElasticBook book) {
        return bookRepository.save(book);
    }

    public Boolean delete(ElasticBook book) {
        bookRepository.delete(book);
        return Boolean.TRUE;
    }

    public Optional<ElasticBook> findById(String id) {
        return bookRepository.findById(id);
    }

    @SneakyThrows
    public BooksQueryResponse findBooks(String title, String description, String genre, String author, String publisher,
            String isbnCode, Double priceIvaMin, Double priceIvaMax,
            Double priceDigitalIvaMin, Double priceDigitalIvaMax,
            Integer valoration, Boolean aggregate) {

        BoolQueryBuilder querySpec = QueryBuilders.boolQuery();

        if (!StringUtils.isEmpty(genre)) {
            querySpec.must(QueryBuilders.termQuery("genre", genre));
        }

        if (!StringUtils.isEmpty(title)) {
            querySpec.must(QueryBuilders.multiMatchQuery(title, "title", "title.search").type(Type.BOOL_PREFIX));
        }

        if (!StringUtils.isEmpty(description)) {
            querySpec.must(QueryBuilders.multiMatchQuery(description, descriptionSearchFields).type(Type.BOOL_PREFIX));
        }

        if (!StringUtils.isEmpty(author)) {
            querySpec.must(QueryBuilders.termQuery("author", author));
        }

        if (!StringUtils.isEmpty(publisher)) {
            querySpec.must(QueryBuilders.termQuery("publisher", publisher));
        }

        if (!StringUtils.isEmpty(isbnCode)) {
            querySpec.must(QueryBuilders.termQuery("isbn_code", isbnCode));
        }

        if (priceIvaMin != null && priceIvaMax != null) {
            querySpec.must(QueryBuilders.rangeQuery("price_iva").gte(priceIvaMin).lte(priceIvaMax));
        } else if (priceIvaMin != null) {
            querySpec.must(QueryBuilders.rangeQuery("price_iva").gte(priceIvaMin));
        } else if (priceIvaMax != null) {
            querySpec.must(QueryBuilders.rangeQuery("price_iva").lte(priceIvaMax));
        }

        if (priceDigitalIvaMin != null && priceDigitalIvaMax != null) {
            querySpec.must(
                    QueryBuilders.rangeQuery("price_digital_iva").gte(priceDigitalIvaMin).lte(priceDigitalIvaMax));
        } else if (priceDigitalIvaMin != null) {
            querySpec.must(QueryBuilders.rangeQuery("price_digital_iva").gte(priceDigitalIvaMin));
        } else if (priceDigitalIvaMax != null) {
            querySpec.must(QueryBuilders.rangeQuery("price_digital_iva").lte(priceDigitalIvaMax));
        }

        if (valoration != null) {
            querySpec.must(QueryBuilders.termQuery("valoration", valoration));
        }

        // Si no hay parámetros, buscamos todos los libros
        if (!querySpec.hasClauses()) {
            querySpec.must(QueryBuilders.matchAllQuery());
        }

        // Filtro implícito para solo mostrar libros visibles
        querySpec.must(QueryBuilders.termQuery("visibility", true));

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withQuery(querySpec);

        if (aggregate) {
            nativeSearchQueryBuilder
                    .addAggregation(AggregationBuilders.terms("Genre Aggregation").field("genre").size(1000));
            nativeSearchQueryBuilder.withMaxResults(0);
        }

        Query query = nativeSearchQueryBuilder.build();
        SearchHits<ElasticBook> result = elasticClient.search(query, ElasticBook.class);

        List<AggregationDetails> responseAggs = new LinkedList<>();

        if (result.hasAggregations()) {
            Map<String, Aggregation> aggs = result.getAggregations().asMap();
            ParsedStringTerms genreAgg = (ParsedStringTerms) aggs.get("Genre Aggregation");

            String queryParams = getQueryParams(title, description, genre, author, publisher, isbnCode,
                    priceIvaMin, priceIvaMax, priceDigitalIvaMin, priceDigitalIvaMax, valoration);
            genreAgg.getBuckets()
                    .forEach(
                            bucket -> responseAggs.add(
                                    new AggregationDetails(
                                            bucket.getKey().toString(),
                                            (int) bucket.getDocCount(),
                                            serverFullAddress + "/books?genre=" + bucket.getKey() + queryParams)));
        }

        return new BooksQueryResponse(result.getSearchHits().stream().map(SearchHit::getContent).toList(),
                responseAggs);
    }

    private String getQueryParams(String title, String description, String genre, String author, String publisher,
            String isbnCode, Double priceIvaMin, Double priceIvaMax,
            Double priceDigitalIvaMin, Double priceDigitalIvaMax, Integer valoration) {
        String queryParams = (StringUtils.isEmpty(title) ? "" : "&title=" + title)
                + (StringUtils.isEmpty(description) ? "" : "&description=" + description)
                + (StringUtils.isEmpty(genre) ? "" : "&genre=" + genre)
                + (StringUtils.isEmpty(author) ? "" : "&author=" + author)
                + (StringUtils.isEmpty(publisher) ? "" : "&publisher=" + publisher)
                + (StringUtils.isEmpty(isbnCode) ? "" : "&isbnCode=" + isbnCode)
                + (priceIvaMin != null ? "&priceIvaMin=" + priceIvaMin : "")
                + (priceIvaMax != null ? "&priceIvaMax=" + priceIvaMax : "")
                + (priceDigitalIvaMin != null ? "&priceDigitalIvaMin=" + priceDigitalIvaMin : "")
                + (priceDigitalIvaMax != null ? "&priceDigitalIvaMax=" + priceDigitalIvaMax : "")
                + (valoration != null ? "&valoration=" + valoration : "");

        return queryParams.endsWith("&") ? queryParams.substring(0, queryParams.length() - 1) : queryParams;
    }

}