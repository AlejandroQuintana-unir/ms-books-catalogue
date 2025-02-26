package com.unir.laboratory.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.unir.laboratory.entity.ElasticBook;

public interface ElasticBookRepository extends ElasticsearchRepository<ElasticBook, String> {

	// Búsqueda de autocompletado con title (Search_As_You_Type)
    @Query("{\"match\": {\"title\": \"?0\"}}")
	List<ElasticBook> findByTitle(String title);

	Optional<ElasticBook> findById(String id);
	
	ElasticBook save(ElasticBook product);
	
	void delete(ElasticBook product);
	
	List<ElasticBook> findAll();
}