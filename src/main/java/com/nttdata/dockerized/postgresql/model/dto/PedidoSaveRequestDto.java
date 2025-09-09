package com.nttdata.dockerized.postgresql.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PedidoSaveRequestDto {

    private Long clientId;
    private List<DetallePedidoRequestDto> detalles;

    @Getter
    @Setter
    public static class DetallePedidoRequestDto {
        private Long productoId;
        private Integer cantidad;
    }
}
