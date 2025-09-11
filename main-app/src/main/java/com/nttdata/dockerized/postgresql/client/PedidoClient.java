package com.nttdata.dockerized.postgresql.client;

import com.nttdata.dockerized.postgresql.model.dto.PedidoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PedidoClient {

    @Autowired
    private RestTemplate restTemplate;

    private static final String PEDIDO_MS_URL = "http://localhost:8082";

    public List<PedidoDto> getAllPedidos() {
        ResponseEntity<List<PedidoDto>> response = restTemplate.exchange(
            PEDIDO_MS_URL + "/api/pedidos",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<PedidoDto>>() {}
        );
        return response.getBody();
    }

    public PedidoDto getPedidoById(Long id) {
        return restTemplate.getForObject(
            PEDIDO_MS_URL + "/api/pedidos/" + id,
            PedidoDto.class
        );
    }
}
