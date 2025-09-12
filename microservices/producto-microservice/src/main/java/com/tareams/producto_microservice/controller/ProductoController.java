package com.tareams.producto_microservice.controller;

import com.tareams.producto_microservice.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductoController {
    
    @GetMapping("/productos")
    public ResponseEntity<ApiResponse<List<Object>>> getAllProductos() {
        // Por ahora retornamos una lista vacía
        List<Object> productos = new ArrayList<>();
        return ResponseEntity.ok(ApiResponse.success("Productos obtenidos exitosamente", productos));
    }
    
    @GetMapping("/categorias")
    public ResponseEntity<ApiResponse<List<Object>>> getAllCategorias() {
        // Por ahora retornamos una lista vacía
        List<Object> categorias = new ArrayList<>();
        return ResponseEntity.ok(ApiResponse.success("Categorías obtenidas exitosamente", categorias));
    }
}
