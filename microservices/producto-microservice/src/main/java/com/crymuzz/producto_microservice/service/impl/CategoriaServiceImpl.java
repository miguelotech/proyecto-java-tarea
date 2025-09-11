package com.crymuzz.producto_microservice.service.impl;

import com.crymuzz.producto_microservice.exception.DuplicateCategoryException;
import com.crymuzz.producto_microservice.exception.ResourceNotFoundException;
import com.crymuzz.producto_microservice.mapper.CategoriaMapper;
import com.crymuzz.producto_microservice.model.dto.CreateCategoriaDTO;
import com.crymuzz.producto_microservice.model.dto.ResponseCategoriaDTO;
import com.crymuzz.producto_microservice.model.dto.UpdateCategoriaDTO;
import com.crymuzz.producto_microservice.model.entity.Categoria;
import com.crymuzz.producto_microservice.repository.CategoriaRepository;
import com.crymuzz.producto_microservice.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    // ACÁ ESTA LA EXCEPCIÓN - HANDLER POR CAMPO O ATRIBUTO (DUPLICATECATEGORY)
    // Y EN ESTA FUNCIÓN SE LANZA
    @Override
    @Transactional
    public ResponseCategoriaDTO crearCategoria(CreateCategoriaDTO createCategoriaDTO) {
        boolean result = categoriaRepository.existsByNombre(createCategoriaDTO.getNombre());
        if (result)
            throw new DuplicateCategoryException(createCategoriaDTO.getNombre());
        Categoria categoria = categoriaMapper.toEntity(createCategoriaDTO);
        categoria = categoriaRepository.save(categoria);
        return categoriaMapper.toDto(categoria);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseCategoriaDTO> listarCategorias() {
        return categoriaMapper.toDtoList(categoriaRepository.findAll());
    }

    @Override
    public ResponseCategoriaDTO obtenerCategoriaId(Long id) {
        return  categoriaMapper.toDto(obtenerEntidadId(id));
    }

    @Override
    public Categoria obtenerEntidadId(Long id) {
        return categoriaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada"));
    }

    @Override
    @Transactional
    public ResponseCategoriaDTO actualizarCategoria(Long id, UpdateCategoriaDTO updateCategoriaDTO) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        categoria.setNombre(updateCategoriaDTO.getNombre());

        Categoria actualizada = categoriaRepository.save(categoria);
        return categoriaMapper.toDto(actualizada);
    }

    @Override
    @Transactional
    public void eliminarCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        categoriaRepository.delete(categoria);
    }
}