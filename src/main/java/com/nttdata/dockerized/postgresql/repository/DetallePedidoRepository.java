package com.nttdata.dockerized.postgresql.repository;

import com.nttdata.dockerized.postgresql.model.entity.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
    
    @Query("SELECT dp.producto.nombre, SUM(dp.cantidad) as totalVendido " +
           "FROM DetallePedido dp " +
           "GROUP BY dp.producto.id, dp.producto.nombre " +
           "ORDER BY totalVendido DESC")
    List<Object[]> findProductosMasVendidos();
    
    @Query("SELECT c.nombre, COUNT(DISTINCT dp.pedido.client.id) as totalClientes " +
           "FROM DetallePedido dp " +
           "JOIN dp.producto.categoria c " +
           "GROUP BY c.id, c.nombre " +
           "ORDER BY totalClientes DESC")
    List<Object[]> findClientesPorCategoria();
}
