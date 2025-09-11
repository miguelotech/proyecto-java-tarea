package com.nttdata.dockerized.postgresql.controller;

import com.nttdata.dockerized.postgresql.exception.ResourceNotFoundException;
import com.nttdata.dockerized.postgresql.model.dto.*;
import com.nttdata.dockerized.postgresql.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.nttdata.dockerized.postgresql.mapper.ProductoMapper.INSTANCE;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public List<ProductoDto> getAllProductos() {
        return productoService.listAll().stream()
                .map(INSTANCE::map)
                .toList();
    }

    @GetMapping("/{id}")
    public ProductoDto getProductoById(@PathVariable final Long id) {
        return productoService.findById(id)
                .map(INSTANCE::map)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "No se encontró el producto con ID: " + id,
                    "Producto",
                    id
                ));
    }

    @GetMapping("/categoria/{categoriaId}")
    public List<ProductoDto> getProductosByCategoria(@PathVariable final Long categoriaId) {
        return productoService.findByCategoria(categoriaId).stream()
                .map(INSTANCE::map)
                .toList();
    }

    @GetMapping("/precio")
    public List<ProductoDto> getProductosByPrecioRango(
            @RequestParam final BigDecimal precioMin,
            @RequestParam final BigDecimal precioMax) {
        return productoService.findByPrecioRango(precioMin, precioMax).stream()
                .map(INSTANCE::map)
                .toList();
    }

    @PostMapping
    public ProductoSaveResponseDto save(@Valid @RequestBody final ProductoSaveRequestDto productoSaveRequestDto) {
        return INSTANCE.toProductoSaveResponseDto(
                productoService.save(INSTANCE.toEntity(productoSaveRequestDto), productoSaveRequestDto.getCategoriaId())
        );
    }

    @PutMapping("/{id}")
    public ProductoSaveResponseDto update(@PathVariable final Long id, 
                                         @Valid @RequestBody final ProductoUpdateRequestDto productoUpdateRequestDto) {
        return productoService.update(id, INSTANCE.toEntity(productoUpdateRequestDto), productoUpdateRequestDto.getCategoriaId())
                .map(INSTANCE::toProductoSaveResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "No se encontró el producto con ID: " + id,
                    "Producto",
                    id
                ));
    }

    @PutMapping("/{productoId}/categoria/{categoriaId}")
    public ProductoDto asignarCategoria(
            @PathVariable final Long productoId,
            @PathVariable final Long categoriaId) {
        return productoService.asignarCategoria(productoId, categoriaId)
                .map(INSTANCE::map)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "No se encontró el producto con ID: " + productoId + " o la categoría con ID: " + categoriaId,
                    "Producto",
                    productoId
                ));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final Long id) {
        productoService.deleteById(id);
    }
}
