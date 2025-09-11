package com.crymuzz.pedido_microservice.mapper;


import com.crymuzz.pedido_microservice.model.dto.CreateDetallePedidoDTO;
import com.crymuzz.pedido_microservice.model.dto.ResponseDetallePedidoDTO;
import com.crymuzz.pedido_microservice.model.entity.DetallePedido;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DetallePedidoMapper {
    DetallePedido toEntity(CreateDetallePedidoDTO createPedidoDTO);
    ResponseDetallePedidoDTO toDto(DetallePedido detalle);
    List<ResponseDetallePedidoDTO> toDtoList(List<DetallePedido> detalles);
}
