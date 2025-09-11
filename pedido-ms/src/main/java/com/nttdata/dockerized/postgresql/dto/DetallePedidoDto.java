package com.nttdata.dockerized.postgresql.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DetallePedidoDto {

    private Long id;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private String productoNombre;
    private Long productoId;
}


