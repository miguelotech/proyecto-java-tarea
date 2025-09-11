package com.nttdata.dockerized.postgresql.controller;

import com.nttdata.dockerized.postgresql.dto.*;
import com.nttdata.dockerized.postgresql.exception.ResourceNotFoundException;
import com.nttdata.dockerized.postgresql.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.nttdata.dockerized.postgresql.mapper.CategoriaMapper.INSTANCE;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public List<CategoriaDto> getAllCategorias() {
        return categoriaService.listAll().stream()
                .map(INSTANCE::map)
                .toList();
    }

    @GetMapping("/{id}")
    public CategoriaDto getCategoriaById(@PathVariable final Long id) {
        return categoriaService.findById(id)
                .map(INSTANCE::map)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "No se encontró la categoría con ID: " + id,
                    "Categoria",
                    id
                ));
    }

    @PostMapping
    public CategoriaSaveResponseDto save(@Valid @RequestBody final CategoriaSaveRequestDto categoriaSaveRequestDto) {
        return INSTANCE.toCategoriaSaveResponseDto(
                categoriaService.save(INSTANCE.toEntity(categoriaSaveRequestDto))
        );
    }

    @PutMapping("/{id}")
    public CategoriaSaveResponseDto update(@PathVariable final Long id, 
                                          @Valid @RequestBody final CategoriaUpdateRequestDto categoriaUpdateRequestDto) {
        return categoriaService.update(id, INSTANCE.toEntity(categoriaUpdateRequestDto))
                .map(INSTANCE::toCategoriaSaveResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "No se encontró la categoría con ID: " + id,
                    "Categoria",
                    id
                ));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final Long id) {
        categoriaService.deleteById(id);
    }
}


