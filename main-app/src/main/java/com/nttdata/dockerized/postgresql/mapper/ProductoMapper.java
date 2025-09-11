package com.nttdata.dockerized.postgresql.mapper;

import com.nttdata.dockerized.postgresql.model.dto.*;
import com.nttdata.dockerized.postgresql.model.entity.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductoMapper {

    ProductoMapper INSTANCE = Mappers.getMapper(ProductoMapper.class);

    ProductoDto map(Producto producto);

    List<ProductoDto> map(List<Producto> productos);

    Producto toEntity(ProductoSaveRequestDto productoSaveRequestDto);

    Producto toEntity(ProductoUpdateRequestDto productoUpdateRequestDto);

    ProductoSaveResponseDto toProductoSaveResponseDto(Producto producto);

}
