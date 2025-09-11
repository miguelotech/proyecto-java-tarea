package com.crymuzz.producto_microservice.controller;

import com.crymuzz.producto_microservice.model.dto.CreateCategoriaDTO;
import com.crymuzz.producto_microservice.model.dto.ResponseCategoriaDTO;
import com.crymuzz.producto_microservice.model.dto.UpdateCategoriaDTO;
import com.crymuzz.producto_microservice.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    public ResponseCategoriaDTO crear(@RequestBody @Valid CreateCategoriaDTO dto) {
        return categoriaService.crearCategoria(dto);
    }

    @GetMapping
    public List<ResponseCategoriaDTO> listar() {
        return categoriaService.listarCategorias();
    }

    @GetMapping("/{id}")
    public ResponseCategoriaDTO obtener(@PathVariable Long id) {
        return categoriaService.obtenerCategoriaId(id);
    }

    @PutMapping("/{id}")
    public ResponseCategoriaDTO actualizar(@PathVariable Long id, @RequestBody @Valid UpdateCategoriaDTO dto) {
        return categoriaService.actualizarCategoria(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable @Valid Long id) {
        categoriaService.eliminarCategoria(id);
    }
}