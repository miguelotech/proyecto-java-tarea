package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.model.entity.DetallePedido;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DetallePedidoService {

    List<DetallePedido> listAll();

    Optional<DetallePedido> findById(Long id);

    List<DetallePedido> findByPedidoId(Long pedidoId);

    List<DetallePedido> findByProductoId(Long productoId);

    List<DetallePedido> findByClientId(Long clientId);

    boolean existsById(Long id);

    // Métodos avanzados para reportes
    List<Map<String, Object>> getProductosMasVendidos();

    List<Map<String, Object>> getClientesPorCategoria();
}
