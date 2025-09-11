package com.crymuzz.producto_microservice.service;

import com.crymuzz.producto_microservice.model.dto.CreateProductoDTO;
import com.crymuzz.producto_microservice.model.dto.ResponseProductDTO;
import com.crymuzz.producto_microservice.model.dto.UpdateProductoDTO;
import com.crymuzz.producto_microservice.model.entity.Producto;

import java.util.List;

public interface ProductoService {
    ResponseProductDTO crearProducto(CreateProductoDTO createProductoDTO);
    List<ResponseProductDTO> listarProductos();
    ResponseProductDTO obtenerProductoId(Long id);
    Producto obtenerEntidadId(Long id);
    ResponseProductDTO actualizarProducto(Long id, UpdateProductoDTO updateProductoDTO);
    void eliminarProducto(Long id);
}
