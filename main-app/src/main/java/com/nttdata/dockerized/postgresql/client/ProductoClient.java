package com.nttdata.dockerized.postgresql.client;

import com.nttdata.dockerized.postgresql.model.dto.ProductoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductoClient {

    @Autowired
    private RestTemplate restTemplate;

    private static final String PRODUCTO_MS_URL = "http://localhost:8083";

    public List<ProductoDto> getAllProductos() {
        ResponseEntity<List<ProductoDto>> response = restTemplate.exchange(
            PRODUCTO_MS_URL + "/api/productos",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<ProductoDto>>() {}
        );
        return response.getBody();
    }

    public ProductoDto getProductoById(Long id) {
        return restTemplate.getForObject(
            PRODUCTO_MS_URL + "/api/productos/" + id,
            ProductoDto.class
        );
    }
}
