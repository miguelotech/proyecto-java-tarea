package com.crymuzz.producto_microservice.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ResponseProductDTO {
    private Long id;
    private String nombre;
    private BigDecimal precio;
    private ResponseCategoriaDTO categoria;
}
