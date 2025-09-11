package com.crymuzz.producto_microservice.service.impl;


import com.crymuzz.producto_microservice.exception.BadRequestException;
import com.crymuzz.producto_microservice.exception.ResourceNotFoundException;
import com.crymuzz.producto_microservice.mapper.ProductoMapper;
import com.crymuzz.producto_microservice.model.dto.CreateProductoDTO;
import com.crymuzz.producto_microservice.model.dto.ResponseProductDTO;
import com.crymuzz.producto_microservice.model.dto.UpdateProductoDTO;
import com.crymuzz.producto_microservice.model.entity.Categoria;
import com.crymuzz.producto_microservice.model.entity.Producto;
import com.crymuzz.producto_microservice.repository.CategoriaRepository;
import com.crymuzz.producto_microservice.repository.ProductoRepository;
import com.crymuzz.producto_microservice.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoMapper productoMapper;

    @Override
    @Transactional
    public ResponseProductDTO crearProducto(CreateProductoDTO dto) {
        boolean existe = productoRepository.existsByNombre(dto.getNombre());
        if (existe)
            throw new BadRequestException("Ya existe un producto con ese nombre");
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        Producto producto = productoMapper.toEntity(dto);
        producto.setCategoria(categoria);

        producto = productoRepository.save(producto);
        return productoMapper.toDto(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseProductDTO> listarProductos() {
        List<Producto> productos = productoRepository.findAll();
        return productoMapper.toDtoList(productos);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseProductDTO obtenerProductoId(Long id) {
        Producto producto = obtenerEntidadId(id);
        return productoMapper.toDto(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public Producto obtenerEntidadId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
    }



    @Override
    @Transactional
    public ResponseProductDTO actualizarProducto(Long id, UpdateProductoDTO updateProductoDTO) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        producto.setNombre(updateProductoDTO.getNombre());
        producto.setPrecio(updateProductoDTO.getPrecio());
        Optional<Categoria> categoria = categoriaRepository.findById(updateProductoDTO.getCategoriaId());
        if(categoria.isEmpty()) {
            throw new ResourceNotFoundException("Categoria no encontrada");
        }
        producto.setCategoria(categoria.get());

        Producto actualizado = productoRepository.save(producto);
        return productoMapper.toDto(actualizado);
    }

    @Override
    @Transactional
    public void eliminarProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
        productoRepository.delete(producto);
    }
}
