package com.crymuzz.pedido_microservice.repository;


import com.crymuzz.pedido_microservice.model.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
