package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.exception.BadRequestException;
import com.nttdata.dockerized.postgresql.model.entity.DetallePedido;
import com.nttdata.dockerized.postgresql.repository.DetallePedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DetallePedidoServiceImpl implements DetallePedidoService {

    private final DetallePedidoRepository detallePedidoRepository;

    @Override
    public List<DetallePedido> listAll() {
        return detallePedidoRepository.findAll();
    }

    @Override
    public Optional<DetallePedido> findById(final Long id) {
        if (id == null || id <= 0) {
            throw new BadRequestException(
                "El ID del detalle de pedido debe ser un número positivo",
                "DetallePedido",
                id
            );
        }
        return detallePedidoRepository.findById(id);
    }

    @Override
    public List<DetallePedido> findByPedidoId(final Long pedidoId) {
        if (pedidoId == null || pedidoId <= 0) {
            throw new BadRequestException(
                "El ID del pedido debe ser un número positivo",
                "DetallePedido",
                pedidoId
            );
        }
        return detallePedidoRepository.findAll().stream()
                .filter(detalle -> detalle.getPedido().getId().equals(pedidoId))
                .toList();
    }

    @Override
    public List<DetallePedido> findByProductoId(final Long productoId) {
        if (productoId == null || productoId <= 0) {
            throw new BadRequestException(
                "El ID del producto debe ser un número positivo",
                "DetallePedido",
                productoId
            );
        }
        return detallePedidoRepository.findAll().stream()
                .filter(detalle -> detalle.getProducto().getId().equals(productoId))
                .toList();
    }

    @Override
    public List<DetallePedido> findByClientId(final Long clientId) {
        if (clientId == null || clientId <= 0) {
            throw new BadRequestException(
                "El ID del cliente debe ser un número positivo",
                "DetallePedido",
                clientId
            );
        }
        return detallePedidoRepository.findAll().stream()
                .filter(detalle -> detalle.getPedido().getClient().getId().equals(clientId))
                .toList();
    }

    @Override
    public boolean existsById(final Long id) {
        return detallePedidoRepository.existsById(id);
    }

    @Override
    public List<Map<String, Object>> getProductosMasVendidos() {
        return detallePedidoRepository.findProductosMasVendidos().stream()
                .map(result -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("productoNombre", result[0]);
                    map.put("totalVendido", result[1]);
                    return map;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getClientesPorCategoria() {
        return detallePedidoRepository.findClientesPorCategoria().stream()
                .map(result -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("categoriaNombre", result[0]);
                    map.put("totalClientes", result[1]);
                    return map;
                })
                .collect(Collectors.toList());
    }
}
