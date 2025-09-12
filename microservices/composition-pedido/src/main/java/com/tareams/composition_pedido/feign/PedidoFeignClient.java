package com.tareams.composition_pedido.feign;

import com.tareams.composition_pedido.dto.ApiResponse;
import com.tareams.composition_pedido.dto.ResponsePedidoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "pedido-microservice", url = "${microservices.pedido.url}")
public interface PedidoFeignClient {
    
    @GetMapping("/api/pedidos")
    ApiResponse<List<ResponsePedidoDTO>> getAllPedidos();
    
    @GetMapping("/api/pedidos/{id}")
    ApiResponse<ResponsePedidoDTO> getPedidoById(@PathVariable("id") Long id);
}
