package com.crymuzz.pedido_microservice.service;



import com.crymuzz.pedido_microservice.model.dto.CreatePedidoDTO;
import com.crymuzz.pedido_microservice.model.dto.ResponsePedidoDTO;

import java.util.List;

public interface PedidoService {
    ResponsePedidoDTO crearPedido(CreatePedidoDTO createPedidoDTO);
    List<ResponsePedidoDTO> listarPedidos();
    ResponsePedidoDTO obtenerPedidoId(Long id);
}
