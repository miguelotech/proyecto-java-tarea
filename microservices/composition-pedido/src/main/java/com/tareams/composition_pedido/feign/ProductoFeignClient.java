package com.tareams.composition_pedido.feign;

import com.tareams.composition_pedido.dto.ApiResponse;
import com.tareams.composition_pedido.dto.ResponseProductDTO;
import com.tareams.composition_pedido.dto.ResponseCategoriaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "producto-microservice", url = "${microservices.producto.url}")
public interface ProductoFeignClient {
    
    @GetMapping("/api/productos")
    ApiResponse<List<ResponseProductDTO>> getAllProductos();
    
    @GetMapping("/api/productos/{id}")
    ApiResponse<ResponseProductDTO> getProductoById(@PathVariable("id") Long id);
    
    @GetMapping("/api/categorias")
    ApiResponse<List<ResponseCategoriaDTO>> getAllCategorias();
    
    @GetMapping("/api/categorias/{id}")
    ApiResponse<ResponseCategoriaDTO> getCategoriaById(@PathVariable("id") Long id);
}
