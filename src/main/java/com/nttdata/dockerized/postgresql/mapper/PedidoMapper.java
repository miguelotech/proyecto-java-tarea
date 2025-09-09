package com.nttdata.dockerized.postgresql.mapper;

import com.nttdata.dockerized.postgresql.model.dto.*;
import com.nttdata.dockerized.postgresql.model.entity.Pedido;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;

@Mapper(uses = {DetallePedidoMapper.class})
public interface PedidoMapper {

    PedidoMapper INSTANCE = Mappers.getMapper(PedidoMapper.class);

    @Mapping(target = "clienteNombre", source = "client.name")
    @Mapping(target = "clienteId", source = "client.id")
    @Mapping(target = "detalles", source = "detalles")
    PedidoDto map(Pedido pedido);

    List<PedidoDto> map(List<Pedido> pedidos);

    @Mapping(target = "client", ignore = true)
    @Mapping(target = "detalles", ignore = true)
    Pedido toEntity(PedidoSaveRequestDto pedidoSaveRequestDto);

    @Mapping(target = "client", ignore = true)
    @Mapping(target = "detalles", ignore = true)
    Pedido toEntity(PedidoUpdateRequestDto pedidoUpdateRequestDto);

    @Mapping(target = "clienteNombre", source = "client.name")
    @Mapping(target = "detalles", source = "detalles")
    PedidoSaveResponseDto toPedidoSaveResponseDto(Pedido pedido);

    @AfterMapping
    default void setRemainingValues(final Pedido pedido, @MappingTarget final PedidoDto pedidoDto) {
        pedidoDto.setEstado(
                Optional.ofNullable(pedido.getEstado())
                        .map(Enum::name)
                        .orElse("PENDIENTE")
        );
    }
}
