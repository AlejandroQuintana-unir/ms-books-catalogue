package com.unir.laboratory.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AggregationDetails {

    private String key;  // Nombre de la agregación (por ejemplo, el género del libro)
    private Integer count;  // Cantidad de libros en esa categoría
    private String uri;  // URL de consulta filtrada por esta agregación

}
