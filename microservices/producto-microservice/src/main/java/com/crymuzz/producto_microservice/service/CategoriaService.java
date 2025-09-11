package com.crymuzz.producto_microservice.service;

import com.crymuzz.producto_microservice.model.dto.CreateCategoriaDTO;
import com.crymuzz.producto_microservice.model.dto.ResponseCategoriaDTO;
import com.crymuzz.producto_microservice.model.dto.UpdateCategoriaDTO;
import com.crymuzz.producto_microservice.model.entity.Categoria;

import java.util.List;

public interface CategoriaService {
    ResponseCategoriaDTO crearCategoria(CreateCategoriaDTO createCategoriaDTO);
    List<ResponseCategoriaDTO> listarCategorias();
    ResponseCategoriaDTO obtenerCategoriaId(Long id);
    Categoria obtenerEntidadId(Long id);
    ResponseCategoriaDTO actualizarCategoria(Long id, UpdateCategoriaDTO updateCategoriaDTO);
    void eliminarCategoria(Long id);
}