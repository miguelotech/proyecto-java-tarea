package com.nttdata.dockerized.postgresql.model.dto;

import com.nttdata.dockerized.postgresql.model.entity.Pedido;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoUpdateRequestDto {

    private Pedido.EstadoPedido estado;
}
