package com.nttdata.dockerized.postgresql.repository;

import com.nttdata.dockerized.postgresql.entity.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
    
    List<DetallePedido> findByPedidoId(Long pedidoId);
    
    List<DetallePedido> findByProductoId(Long productoId);
    
    @Query("SELECT dp FROM DetallePedido dp WHERE dp.pedido.id = :pedidoId")
    List<DetallePedido> findByPedidoIdWithDetails(@Param("pedidoId") Long pedidoId);
}


