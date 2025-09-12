package com.tareams.composition_pedido.service;

import com.tareams.composition_pedido.dto.*;
import com.tareams.composition_pedido.exception.CompositionException;
import com.tareams.composition_pedido.feign.PedidoFeignClient;
import com.tareams.composition_pedido.feign.ProductoFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompositionServiceImpl implements CompositionService {
    
    @Autowired
    private PedidoFeignClient pedidoFeignClient;
    
    @Autowired
    private ProductoFeignClient productoFeignClient;
    
    @Override
    public List<ResponsePedidoDTO> getAllPedidosCompletos() {
        try {
            ApiResponse<List<ResponsePedidoDTO>> response = pedidoFeignClient.getAllPedidos();
            if (response.isSuccess()) {
                return response.getData();
            } else {
                throw new CompositionException("Error al obtener pedidos: " + response.getMessage());
            }
        } catch (Exception e) {
            throw new CompositionException("Error al obtener pedidos completos: " + e.getMessage(), e);
        }
    }
    
    @Override
    public ResponsePedidoDTO getPedidoCompletoById(Long id) {
        try {
            ApiResponse<ResponsePedidoDTO> response = pedidoFeignClient.getPedidoById(id);
            if (response.isSuccess()) {
                return response.getData();
            } else {
                throw new CompositionException("Error al obtener pedido: " + response.getMessage());
            }
        } catch (Exception e) {
            throw new CompositionException("Error al obtener pedido completo: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<ResponseProductDTO> getAllProductos() {
        try {
            ApiResponse<List<ResponseProductDTO>> response = productoFeignClient.getAllProductos();
            if (response.isSuccess()) {
                return response.getData();
            } else {
                throw new CompositionException("Error al obtener productos: " + response.getMessage());
            }
        } catch (Exception e) {
            throw new CompositionException("Error al obtener productos: " + e.getMessage(), e);
        }
    }
    
    @Override
    public ResponseProductDTO getProductoById(Long id) {
        try {
            ApiResponse<ResponseProductDTO> response = productoFeignClient.getProductoById(id);
            if (response.isSuccess()) {
                return response.getData();
            } else {
                throw new CompositionException("Error al obtener producto: " + response.getMessage());
            }
        } catch (Exception e) {
            throw new CompositionException("Error al obtener producto: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<ResponseCategoriaDTO> getAllCategorias() {
        try {
            ApiResponse<List<ResponseCategoriaDTO>> response = productoFeignClient.getAllCategorias();
            if (response.isSuccess()) {
                return response.getData();
            } else {
                throw new CompositionException("Error al obtener categorías: " + response.getMessage());
            }
        } catch (Exception e) {
            throw new CompositionException("Error al obtener categorías: " + e.getMessage(), e);
        }
    }
    
    @Override
    public ResponseCategoriaDTO getCategoriaById(Long id) {
        try {
            ApiResponse<ResponseCategoriaDTO> response = productoFeignClient.getCategoriaById(id);
            if (response.isSuccess()) {
                return response.getData();
            } else {
                throw new CompositionException("Error al obtener categoría: " + response.getMessage());
            }
        } catch (Exception e) {
            throw new CompositionException("Error al obtener categoría: " + e.getMessage(), e);
        }
    }
}
