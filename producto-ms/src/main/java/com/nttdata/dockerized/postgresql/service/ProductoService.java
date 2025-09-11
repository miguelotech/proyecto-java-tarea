package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.entity.Producto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductoService {

    List<Producto> listAll();

    List<Producto> listAllDisponibles();

    Optional<Producto> findById(Long id);

    List<Producto> findByCategoria(Long categoriaId);

    List<Producto> findByCategoriaNombre(String categoriaNombre);

    List<Producto> findByPrecioRango(BigDecimal precioMin, BigDecimal precioMax);

    List<Producto> findByNombre(String nombre);

    List<Producto> findProductosEnStock();

    Producto save(Producto producto, Long categoriaId);

    Optional<Producto> update(Long id, Producto producto, Long categoriaId);

    boolean deleteById(Long id);

    boolean existsById(Long id);

    Optional<Producto> asignarCategoria(Long productoId, Long categoriaId);
}


