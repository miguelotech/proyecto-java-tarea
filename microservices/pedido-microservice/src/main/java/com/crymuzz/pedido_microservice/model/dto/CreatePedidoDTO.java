package com.crymuzz.pedido_microservice.model.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreatePedidoDTO {
    @NotNull(message = "El cliente es obligatorio")
    private Long clienteId;

    @NotNull(message = "La fecha del pedido es obligatoria")
    @FutureOrPresent(message = "La fecha del pedido no puede ser anterior a hoy")
    private LocalDate fechaPedido;

    @NotEmpty(message = "Debe incluir al menos un detalle")
    private List<CreateDetallePedidoDTO> detalles;
}