package com.crymuzz.pedido_microservice.client;


import com.crymuzz.pedido_microservice.model.dto.ApiResponse;
import com.crymuzz.pedido_microservice.model.dto.ResponseProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "producto-service", url = "http://producto-microservice:8080/api")
public interface ProductoClient {
    @GetMapping("/productos/{id}")
    ApiResponse<ResponseProductDTO> obtenerProductoPorId(@PathVariable Long id);
}