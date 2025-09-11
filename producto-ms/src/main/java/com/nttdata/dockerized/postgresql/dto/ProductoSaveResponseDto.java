package com.nttdata.dockerized.postgresql.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductoSaveResponseDto {

    private Long id;
    private String nombre;
    private BigDecimal precio;
    private String categoriaNombre;
}


