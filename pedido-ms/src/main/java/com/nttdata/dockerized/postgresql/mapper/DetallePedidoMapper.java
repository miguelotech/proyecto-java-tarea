package com.nttdata.dockerized.postgresql.mapper;

import com.nttdata.dockerized.postgresql.dto.DetallePedidoDto;
import com.nttdata.dockerized.postgresql.entity.DetallePedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DetallePedidoMapper {

    DetallePedidoMapper INSTANCE = Mappers.getMapper(DetallePedidoMapper.class);

    @Mapping(target = "productoNombre", ignore = true)
    @Mapping(target = "productoId", source = "productoId")
    DetallePedidoDto map(DetallePedido detallePedido);

    List<DetallePedidoDto> map(List<DetallePedido> detallesPedido);

    @Mapping(target = "productoId", source = "productoId")
    @Mapping(target = "pedido", ignore = true)
    DetallePedido toEntity(DetallePedidoDto detallePedidoDto);
}


