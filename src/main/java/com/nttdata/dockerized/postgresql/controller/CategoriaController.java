package com.nttdata.dockerized.postgresql.controller;

import com.nttdata.dockerized.postgresql.model.dto.*;
import com.nttdata.dockerized.postgresql.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CategoriaDto> getCategoriaById(@PathVariable final Long id) {
        return categoriaService.findById(id)
                .map(INSTANCE::map)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CategoriaSaveResponseDto save(@RequestBody final CategoriaSaveRequestDto categoriaSaveRequestDto) {
        return INSTANCE.toCategoriaSaveResponseDto(
                categoriaService.save(INSTANCE.toEntity(categoriaSaveRequestDto))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaSaveResponseDto> update(@PathVariable final Long id, 
                                                          @RequestBody final CategoriaUpdateRequestDto categoriaUpdateRequestDto) {
        return categoriaService.update(id, INSTANCE.toEntity(categoriaUpdateRequestDto))
                .map(INSTANCE::toCategoriaSaveResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        return categoriaService.deleteById(id) 
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
