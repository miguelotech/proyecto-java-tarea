package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.model.dto.PedidoSaveRequestDto;
import com.nttdata.dockerized.postgresql.model.entity.Pedido;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PedidoService {

    List<Pedido> listAll();

    Optional<Pedido> findById(Long id);

    List<Pedido> findByClientId(Long clientId);

    List<Pedido> findByEstado(Pedido.EstadoPedido estado);

    List<Pedido> findByFechaRango(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    Pedido crearPedido(PedidoSaveRequestDto pedidoSaveRequestDto);

    Optional<Pedido> updateEstado(Long id, Pedido.EstadoPedido estado);

    Optional<Pedido> update(Long id, Pedido pedido);

    boolean deleteById(Long id);

    boolean existsById(Long id);

    Long countPedidosByClient(Long clientId);

    List<Pedido> findHistorialByClient(Long clientId);
}
