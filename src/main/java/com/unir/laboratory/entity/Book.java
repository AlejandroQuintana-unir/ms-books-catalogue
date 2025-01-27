package com.unir.laboratory.entity;

import java.util.Date;

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
    private Integer edition;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "publish_date")
    private Date publishDate;

    @Column(name = "isbn_code")
    private String isbnCode;

    @Column(name = "price")
    private Double price;

    @Column(name = "price_iva")
    private Double priceIva;

    @Column(name = "price_digital")
    private Double priceDigital;

    @Column(name = "price_digital_iva")
    private Double priceDigitalIva;

    @Column(name = "genre")
    private String genre;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "valoration")
    private Integer valoration;

    @Column(name = "visibility")
    private Boolean visibility;

    public void update(Book book) {
        this.author = book.getAuthor();
        this.title = book.getTitle();
        this.edition = book.getEdition();
        this.publisher = book.getPublisher();
        this.publishDate = book.getPublishDate();
        this.isbnCode = book.getIsbnCode();
        this.price = book.getPrice();
        this.priceIva = book.getPriceIva();
        this.priceDigital = book.getPriceDigital();
        this.priceDigitalIva = book.getPriceDigitalIva();
        this.genre = book.getGenre();
        this.description = book.getDescription();
        this.stock = book.getStock();
        this.valoration = book.getValoration();
        this.visibility = book.getVisibility();
    }

}
