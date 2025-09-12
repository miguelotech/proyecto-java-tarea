package com.tareams.composition_pedido.service;

import com.tareams.composition_pedido.dto.ResponsePedidoDTO;
import com.tareams.composition_pedido.dto.ResponseProductDTO;
import com.tareams.composition_pedido.dto.ResponseCategoriaDTO;

import java.util.List;

public interface CompositionService {
    
    List<ResponsePedidoDTO> getAllPedidosCompletos();
    
    ResponsePedidoDTO getPedidoCompletoById(Long id);
    
    List<ResponseProductDTO> getAllProductos();
    
    ResponseProductDTO getProductoById(Long id);
    
    List<ResponseCategoriaDTO> getAllCategorias();
    
    ResponseCategoriaDTO getCategoriaById(Long id);
}
