package com.nttdata.dockerized.postgresql.dto;

import com.nttdata.dockerized.postgresql.entity.Pedido;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoUpdateRequestDto {

    @NotNull(message = "El estado del pedido es requerido")
    private Pedido.EstadoPedido estado;
}


