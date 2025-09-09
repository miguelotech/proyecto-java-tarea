package com.nttdata.dockerized.postgresql.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductoDto {

    private Long id;
    private String nombre;
    private BigDecimal precio;
    private String categoriaNombre;
    private Long categoriaId;
}
