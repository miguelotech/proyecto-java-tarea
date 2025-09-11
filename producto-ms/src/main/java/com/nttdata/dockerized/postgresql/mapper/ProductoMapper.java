package com.nttdata.dockerized.postgresql.mapper;

import com.nttdata.dockerized.postgresql.dto.*;
import com.nttdata.dockerized.postgresql.entity.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductoMapper {

    ProductoMapper INSTANCE = Mappers.getMapper(ProductoMapper.class);

    @Mapping(target = "categoriaNombre", source = "categoria.nombre")
    @Mapping(target = "categoriaId", source = "categoria.id")
    ProductoDto map(Producto producto);

    List<ProductoDto> map(List<Producto> productos);

    @Mapping(target = "categoria", ignore = true)
    Producto toEntity(ProductoSaveRequestDto productoSaveRequestDto);

    @Mapping(target = "categoria", ignore = true)
    Producto toEntity(ProductoUpdateRequestDto productoUpdateRequestDto);

    @Mapping(target = "categoriaNombre", source = "categoria.nombre")
    ProductoSaveResponseDto toProductoSaveResponseDto(Producto producto);

}


