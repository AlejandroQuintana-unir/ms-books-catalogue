package com.unir.laboratory.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = "books", createIndex = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ElasticBook {

    @Id
    private String id;

    @Field(type = FieldType.Keyword, name = "author")
    private String author;

    @Field(type = FieldType.Search_As_You_Type, name = "title")
    private String title;

    @Field(type = FieldType.Integer, name = "edition")
    private Integer edition;

    @Field(type = FieldType.Keyword, name = "publisher")
    private String publisher;

    @Field(type = FieldType.Date, name = "publish_date")
    private Date publishDate;

    @Field(type = FieldType.Keyword, name = "isbn_code")
    private String isbnCode;

    @Field(type = FieldType.Double, name = "price")
    private Double price;

    @Field(type = FieldType.Double, name = "price_iva")
    private Double priceIva;

    @Field(type = FieldType.Double, name = "price_digital")
    private Double priceDigital;

    @Field(type = FieldType.Double, name = "price_digital_iva")
    private Double priceDigitalIva;

    @Field(type = FieldType.Keyword, name = "genre")
    private String genre;

    @Field(type = FieldType.Search_As_You_Type, name = "description")
    private String description;

    @Field(type = FieldType.Integer, name = "stock")
    private Integer stock;

    @Field(type = FieldType.Integer, name = "valoration")
    private Integer valoration;

    @Field(type = FieldType.Boolean, name = "visibility")
    private Boolean visibility;

}