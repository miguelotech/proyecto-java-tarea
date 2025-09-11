package com.nttdata.dockerized.postgresql.mapper;

import com.nttdata.dockerized.postgresql.model.dto.DetallePedidoDto;
import com.nttdata.dockerized.postgresql.model.entity.DetallePedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DetallePedidoMapper {

    DetallePedidoMapper INSTANCE = Mappers.getMapper(DetallePedidoMapper.class);

    DetallePedidoDto map(DetallePedido detallePedido);

    List<DetallePedidoDto> map(List<DetallePedido> detallesPedido);

    DetallePedido toEntity(DetallePedidoDto detallePedidoDto);
}
