package com.tareams.pedido_microservice.controller;

import com.tareams.pedido_microservice.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PedidoController {
    
    @GetMapping("/pedidos")
    public ResponseEntity<ApiResponse<List<Object>>> getAllPedidos() {
        // Por ahora retornamos una lista vacía
        List<Object> pedidos = new ArrayList<>();
        return ResponseEntity.ok(ApiResponse.success("Pedidos obtenidos exitosamente", pedidos));
    }
}
