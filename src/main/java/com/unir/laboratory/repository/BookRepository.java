package com.unir.laboratory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unir.laboratory.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

} 
