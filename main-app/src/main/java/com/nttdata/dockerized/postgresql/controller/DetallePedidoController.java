package com.nttdata.dockerized.postgresql.controller;

import com.nttdata.dockerized.postgresql.model.dto.DetallePedidoDto;
import com.nttdata.dockerized.postgresql.service.DetallePedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.nttdata.dockerized.postgresql.mapper.DetallePedidoMapper.INSTANCE;

/**
 * Controlador REST para detalles de pedidos y reportes
 */
@RestController
@RequestMapping("/api/detalle-pedidos")
@RequiredArgsConstructor
public class DetallePedidoController {

    private final DetallePedidoService detallePedidoService;

    // Listar detalles por pedido
    @GetMapping("/pedido/{pedidoId}")
    public List<DetallePedidoDto> getDetallesByPedido(@PathVariable final Long pedidoId) {
        return detallePedidoService.findByPedidoId(pedidoId).stream()
                .map(INSTANCE::map)
                .toList();
    }

    // Listar detalles por producto
    @GetMapping("/producto/{productoId}")
    public List<DetallePedidoDto> getDetallesByProducto(@PathVariable final Long productoId) {
        return detallePedidoService.findByProductoId(productoId).stream()
                .map(INSTANCE::map)
                .toList();
    }

    // Listar detalles por cliente
    @GetMapping("/client/{clientId}")
    public List<DetallePedidoDto> getDetallesByClient(@PathVariable final Long clientId) {
        return detallePedidoService.findByClientId(clientId).stream()
                .map(INSTANCE::map)
                .toList();
    }

    // Reporte: Productos más vendidos
    @GetMapping("/reportes/productos-mas-vendidos")
    public List<Map<String, Object>> getProductosMasVendidos() {
        return detallePedidoService.getProductosMasVendidos();
    }

    // Reporte: Clientes por categoría
    @GetMapping("/reportes/clientes-por-categoria")
    public List<Map<String, Object>> getClientesPorCategoria() {
        return detallePedidoService.getClientesPorCategoria();
    }
}
