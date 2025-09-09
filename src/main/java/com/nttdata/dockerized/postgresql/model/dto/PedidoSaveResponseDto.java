package com.nttdata.dockerized.postgresql.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoSaveResponseDto {

    private Long id;
    private LocalDateTime fechaPedido;
    private String estado;
    private String clienteNombre;
    private List<DetallePedidoDto> detalles;
}
