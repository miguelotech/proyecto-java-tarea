package com.nttdata.dockerized.postgresql.controller;

import com.nttdata.dockerized.postgresql.model.dto.*;
import com.nttdata.dockerized.postgresql.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.nttdata.dockerized.postgresql.mapper.PedidoMapper.INSTANCE;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping
    public List<PedidoDto> getAllPedidos() {
        return pedidoService.listAll().stream()
                .map(INSTANCE::map)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDto> getPedidoById(@PathVariable final Long id) {
        return pedidoService.findById(id)
                .map(INSTANCE::map)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Pedidos de un cliente
    @GetMapping("/client/{clientId}")
    public List<PedidoDto> getPedidosByClient(@PathVariable final Long clientId) {
        return pedidoService.findByClientId(clientId).stream()
                .map(INSTANCE::map)
                .toList();
    }

    // Historial de pedidos ordenado por fecha
    @GetMapping("/client/{clientId}/historial")
    public List<PedidoDto> getHistorialPedidosByClient(@PathVariable final Long clientId) {
        return pedidoService.findHistorialByClient(clientId).stream()
                .map(INSTANCE::map)
                .toList();
    }

    // Pedidos por estado
    @GetMapping("/estado/{estado}")
    public List<PedidoDto> getPedidosByEstado(@PathVariable final String estado) {
        return pedidoService.findByEstado(com.nttdata.dockerized.postgresql.model.entity.Pedido.EstadoPedido.valueOf(estado)).stream()
                .map(INSTANCE::map)
                .toList();
    }

    // Contar pedidos de un cliente
    @GetMapping("/client/{clientId}/count")
    public Long countPedidosByClient(@PathVariable final Long clientId) {
        return pedidoService.countPedidosByClient(clientId);
    }

    @PostMapping
    public PedidoSaveResponseDto crearPedido(@RequestBody final PedidoSaveRequestDto pedidoSaveRequestDto) {
        return INSTANCE.toPedidoSaveResponseDto(
                pedidoService.crearPedido(pedidoSaveRequestDto)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDto> update(@PathVariable final Long id, 
                                           @RequestBody final PedidoUpdateRequestDto pedidoUpdateRequestDto) {
        return pedidoService.update(id, INSTANCE.toEntity(pedidoUpdateRequestDto))
                .map(INSTANCE::map)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        return pedidoService.deleteById(id) 
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
