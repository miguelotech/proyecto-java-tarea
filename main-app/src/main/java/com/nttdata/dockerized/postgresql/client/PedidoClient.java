package com.nttdata.dockerized.postgresql.client;

import com.nttdata.dockerized.postgresql.model.dto.PedidoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "pedido-ms", url = "http://localhost:8082")
public interface PedidoClient {

    @GetMapping("/api/pedidos")
    List<PedidoDto> getAllPedidos();

    @GetMapping("/api/pedidos/{id}")
    PedidoDto getPedidoById(@PathVariable("id") Long id);
}
