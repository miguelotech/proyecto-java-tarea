package com.crymuzz.pedido_microservice.service;



import com.crymuzz.pedido_microservice.model.dto.ResponseDetallePedidoDTO;

import java.util.List;

public interface DetallePedidoService {
    List<ResponseDetallePedidoDTO> listarPorPedidoId(Long id);
}
