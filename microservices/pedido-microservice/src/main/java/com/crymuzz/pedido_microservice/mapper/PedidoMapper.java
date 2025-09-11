package com.crymuzz.pedido_microservice.mapper;


import com.crymuzz.pedido_microservice.model.dto.CreatePedidoDTO;
import com.crymuzz.pedido_microservice.model.dto.ResponsePedidoDTO;
import com.crymuzz.pedido_microservice.model.entity.Pedido;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class, DetallePedidoMapper.class})
public interface PedidoMapper {
    Pedido toEntity(CreatePedidoDTO createPedidoDTO);
    ResponsePedidoDTO toDto(Pedido pedido);
    List<ResponsePedidoDTO> toDtoList(List<Pedido> pedidos);
}
