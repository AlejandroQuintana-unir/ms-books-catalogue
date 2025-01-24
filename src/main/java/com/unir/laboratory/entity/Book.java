package com.unir.laboratory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "author")
    private String author;
	
	@Column(name = "title")
	private String title;
    
	@Column(name = "edition")
    private int edition;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "price")
    private double price;

    @Column(name = "price_iva")
    private double priceIva;

    @Column(name = "price_digital")
    private double priceDigital;

    @Column(name = "price_digital_iva")
    private double priceDigitalIva;

    @Column(name = "genre")
    private String genre;

    @Column(name = "description",length = 500)
    private String description;

    @Column(name = "stock")
    private int stock;

}
