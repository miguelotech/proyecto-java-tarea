package com.nttdata.dockerized.postgresql.client;

import com.nttdata.dockerized.postgresql.model.dto.ProductoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "producto-ms", url = "http://localhost:8083")
public interface ProductoClient {

    @GetMapping("/api/productos")
    List<ProductoDto> getAllProductos();

    @GetMapping("/api/productos/{id}")
    ProductoDto getProductoById(@PathVariable("id") Long id);
}
