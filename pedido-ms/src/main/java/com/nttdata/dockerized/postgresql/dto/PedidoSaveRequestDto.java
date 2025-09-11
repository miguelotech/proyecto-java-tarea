package com.nttdata.dockerized.postgresql.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PedidoSaveRequestDto {

    @NotNull(message = "El ID del cliente es requerido")
    private Long clientId;
    
    @NotEmpty(message = "El pedido debe tener al menos un detalle")
    @Valid
    private List<DetallePedidoRequestDto> detalles;

    @Getter
    @Setter
    public static class DetallePedidoRequestDto {
        @NotNull(message = "El ID del producto es requerido")
        private Long productoId;
        
        @NotNull(message = "La cantidad es requerida")
        @Min(value = 1, message = "La cantidad debe ser mayor que 0")
        private Integer cantidad;
    }
}


