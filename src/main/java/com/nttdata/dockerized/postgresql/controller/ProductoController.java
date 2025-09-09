package com.nttdata.dockerized.postgresql.controller;

import com.nttdata.dockerized.postgresql.model.dto.*;
import com.nttdata.dockerized.postgresql.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ProductoDto> getProductoById(@PathVariable final Long id) {
        return productoService.findById(id)
                .map(INSTANCE::map)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
    public ProductoSaveResponseDto save(@RequestBody final ProductoSaveRequestDto productoSaveRequestDto) {
        return INSTANCE.toProductoSaveResponseDto(
                productoService.save(INSTANCE.toEntity(productoSaveRequestDto), productoSaveRequestDto.getCategoriaId())
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoSaveResponseDto> update(@PathVariable final Long id, 
                                                         @RequestBody final ProductoUpdateRequestDto productoUpdateRequestDto) {
        return productoService.update(id, INSTANCE.toEntity(productoUpdateRequestDto), productoUpdateRequestDto.getCategoriaId())
                .map(INSTANCE::toProductoSaveResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{productoId}/categoria/{categoriaId}")
    public ResponseEntity<ProductoDto> asignarCategoria(
            @PathVariable final Long productoId,
            @PathVariable final Long categoriaId) {
        return productoService.asignarCategoria(productoId, categoriaId)
                .map(INSTANCE::map)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        return productoService.deleteById(id) 
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
