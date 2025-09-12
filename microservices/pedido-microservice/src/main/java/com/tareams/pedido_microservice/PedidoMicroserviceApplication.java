package com.tareams.pedido_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PedidoMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PedidoMicroserviceApplication.class, args);
    }

}
