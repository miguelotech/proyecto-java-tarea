package com.nttdata.dockerized.postgresql.repository;

import com.nttdata.dockerized.postgresql.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    List<Producto> findByCategoriaId(Long categoriaId);
    
    List<Producto> findByPrecioBetween(BigDecimal precioMin, BigDecimal precioMax);
    
    @Query("SELECT p FROM Producto p WHERE p.categoria.id = :categoriaId AND p.precio BETWEEN :precioMin AND :precioMax")
    List<Producto> findByCategoriaIdAndPrecioBetween(
        @Param("categoriaId") Long categoriaId, 
        @Param("precioMin") BigDecimal precioMin, 
        @Param("precioMax") BigDecimal precioMax
    );
}


