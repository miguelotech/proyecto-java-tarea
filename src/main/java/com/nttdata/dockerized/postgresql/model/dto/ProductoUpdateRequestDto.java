package com.nttdata.dockerized.postgresql.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductoUpdateRequestDto {

    private String nombre;
    private BigDecimal precio;
    private Long categoriaId;
}
