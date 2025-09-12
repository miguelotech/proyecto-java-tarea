package com.tareams.composition_pedido;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CompositionPedidoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompositionPedidoApplication.class, args);
    }

}
