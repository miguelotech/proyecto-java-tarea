package com.nttdata.dockerized.postgresql.controller;

import com.nttdata.dockerized.postgresql.client.PedidoClient;
import com.nttdata.dockerized.postgresql.client.ProductoClient;
import com.nttdata.dockerized.postgresql.model.dto.PedidoDto;
import com.nttdata.dockerized.postgresql.model.dto.ProductoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/integration")
public class IntegrationController {

    @Autowired
    private ProductoClient productoClient;

    @Autowired
    private PedidoClient pedidoClient;

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboard() {
        Map<String, Object> dashboard = new HashMap<>();
        
        try {
            List<ProductoDto> productos = productoClient.getAllProductos();
            List<PedidoDto> pedidos = pedidoClient.getAllPedidos();
            
            dashboard.put("productos", productos);
            dashboard.put("pedidos", pedidos);
            dashboard.put("totalProductos", productos.size());
            dashboard.put("totalPedidos", pedidos.size());
            dashboard.put("status", "success");
            
            return ResponseEntity.ok(dashboard);
        } catch (Exception e) {
            dashboard.put("status", "error");
            dashboard.put("message", "Error al obtener datos de los microservicios: " + e.getMessage());
            return ResponseEntity.status(500).body(dashboard);
        }
    }

}
