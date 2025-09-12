package com.tareams.composition_pedido.controller;

import com.tareams.composition_pedido.dto.ApiResponse;
import com.tareams.composition_pedido.dto.ResponsePedidoDTO;
import com.tareams.composition_pedido.dto.ResponseProductDTO;
import com.tareams.composition_pedido.dto.ResponseCategoriaDTO;
import com.tareams.composition_pedido.service.CompositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/composition")
@CrossOrigin(origins = "*")
public class CompositionController {
    
    @Autowired
    private CompositionService compositionService;
    
    @GetMapping("/pedidos")
    public ResponseEntity<ApiResponse<List<ResponsePedidoDTO>>> getAllPedidos() {
        try {
            List<ResponsePedidoDTO> pedidos = compositionService.getAllPedidosCompletos();
            return ResponseEntity.ok(ApiResponse.success("Pedidos obtenidos exitosamente", pedidos));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.error("Error al obtener pedidos: " + e.getMessage()));
        }
    }
    
    @GetMapping("/pedidos/{id}")
    public ResponseEntity<ApiResponse<ResponsePedidoDTO>> getPedidoById(@PathVariable Long id) {
        try {
            ResponsePedidoDTO pedido = compositionService.getPedidoCompletoById(id);
            return ResponseEntity.ok(ApiResponse.success("Pedido obtenido exitosamente", pedido));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.error("Error al obtener pedido: " + e.getMessage()));
        }
    }
    
    @GetMapping("/productos")
    public ResponseEntity<ApiResponse<List<ResponseProductDTO>>> getAllProductos() {
        try {
            List<ResponseProductDTO> productos = compositionService.getAllProductos();
            return ResponseEntity.ok(ApiResponse.success("Productos obtenidos exitosamente", productos));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.error("Error al obtener productos: " + e.getMessage()));
        }
    }
    
    @GetMapping("/productos/{id}")
    public ResponseEntity<ApiResponse<ResponseProductDTO>> getProductoById(@PathVariable Long id) {
        try {
            ResponseProductDTO producto = compositionService.getProductoById(id);
            return ResponseEntity.ok(ApiResponse.success("Producto obtenido exitosamente", producto));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.error("Error al obtener producto: " + e.getMessage()));
        }
    }
    
    @GetMapping("/categorias")
    public ResponseEntity<ApiResponse<List<ResponseCategoriaDTO>>> getAllCategorias() {
        try {
            List<ResponseCategoriaDTO> categorias = compositionService.getAllCategorias();
            return ResponseEntity.ok(ApiResponse.success("Categorías obtenidas exitosamente", categorias));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.error("Error al obtener categorías: " + e.getMessage()));
        }
    }
    
    @GetMapping("/categorias/{id}")
    public ResponseEntity<ApiResponse<ResponseCategoriaDTO>> getCategoriaById(@PathVariable Long id) {
        try {
            ResponseCategoriaDTO categoria = compositionService.getCategoriaById(id);
            return ResponseEntity.ok(ApiResponse.success("Categoría obtenida exitosamente", categoria));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.error("Error al obtener categoría: " + e.getMessage()));
        }
    }
}
