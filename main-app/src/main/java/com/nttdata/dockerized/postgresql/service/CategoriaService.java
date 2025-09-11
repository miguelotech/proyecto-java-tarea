package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.model.entity.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {

    List<Categoria> listAll();

    List<Categoria> listAllActive();

    Optional<Categoria> findById(Long id);

    Optional<Categoria> findByNombre(String nombre);

    Categoria save(Categoria categoria);

    Optional<Categoria> update(Long id, Categoria categoria);

    boolean deleteById(Long id);

    boolean existsById(Long id);

    List<Categoria> findCategoriasConProductos();
}
