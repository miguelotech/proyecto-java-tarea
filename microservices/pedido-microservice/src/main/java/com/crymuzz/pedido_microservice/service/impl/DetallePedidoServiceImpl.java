package com.crymuzz.pedido_microservice.service.impl;


import com.crymuzz.pedido_microservice.mapper.DetallePedidoMapper;
import com.crymuzz.pedido_microservice.model.dto.ResponseDetallePedidoDTO;
import com.crymuzz.pedido_microservice.model.entity.DetallePedido;
import com.crymuzz.pedido_microservice.repository.DetallePedidoRepository;
import com.crymuzz.pedido_microservice.service.DetallePedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DetallePedidoServiceImpl implements DetallePedidoService {
    private final DetallePedidoRepository detallePedidoRepository;
    private final DetallePedidoMapper detallePedidoMapper;

    @Override
    public List<ResponseDetallePedidoDTO> listarPorPedidoId(Long pedidoId) {
        List<DetallePedido> detalles = detallePedidoRepository.findByPedidoId(pedidoId);
        return detallePedidoMapper.toDtoList(detalles);
    }
}
