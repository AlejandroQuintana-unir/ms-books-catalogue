package com.unir.laboratory.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookRequest {

    private String title;
    private String author;
    private Integer edition;
    private String publisher;
    private Date publishDate;
    private String isbnCode;
    private Double price;
    private Double priceIva;
    private Double priceDigital;
    private Double priceDigitalIva;
    private String genre;
    private String description;
    private Integer stock;
    private Integer valoration;
    private Boolean visibility;
}
