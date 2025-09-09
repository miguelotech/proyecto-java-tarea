package com.nttdata.dockerized.postgresql.repository;

import com.nttdata.dockerized.postgresql.model.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByClientId(Long clientId);
    
    @Query("SELECT p FROM Pedido p WHERE p.client.id = :clientId ORDER BY p.fechaPedido DESC")
    List<Pedido> findByClientIdOrderByFechaPedidoDesc(@Param("clientId") Long clientId);
}
