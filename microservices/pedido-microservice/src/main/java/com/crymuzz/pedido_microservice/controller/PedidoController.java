package com.crymuzz.pedido_microservice.controller;


import com.crymuzz.pedido_microservice.model.dto.CreatePedidoDTO;
import com.crymuzz.pedido_microservice.model.dto.ResponsePedidoDTO;
import com.crymuzz.pedido_microservice.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponsePedidoDTO crear(@RequestBody @Valid CreatePedidoDTO dto) {
        return pedidoService.crearPedido(dto);
    }

    @GetMapping
    public List<ResponsePedidoDTO> listar() {
        return pedidoService.listarPedidos();
    }

    @GetMapping("/{id}")
    public ResponsePedidoDTO obtener(@PathVariable Long id) {
        return pedidoService.obtenerPedidoId(id);
    }
}