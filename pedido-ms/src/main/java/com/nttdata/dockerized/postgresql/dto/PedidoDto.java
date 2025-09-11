package com.nttdata.dockerized.postgresql.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoDto {

    private Long id;
    private LocalDateTime fechaPedido;
    private String estado;
    private String clienteNombre;
    private Long clienteId;
    private List<DetallePedidoDto> detalles;
}


