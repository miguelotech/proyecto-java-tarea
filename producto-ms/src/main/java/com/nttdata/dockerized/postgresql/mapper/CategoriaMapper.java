package com.nttdata.dockerized.postgresql.mapper;

import com.nttdata.dockerized.postgresql.dto.*;
import com.nttdata.dockerized.postgresql.entity.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CategoriaMapper {

    CategoriaMapper INSTANCE = Mappers.getMapper(CategoriaMapper.class);

    CategoriaDto map(Categoria categoria);

    List<CategoriaDto> map(List<Categoria> categorias);

    Categoria toEntity(CategoriaSaveRequestDto categoriaSaveRequestDto);

    Categoria toEntity(CategoriaUpdateRequestDto categoriaUpdateRequestDto);

    CategoriaSaveResponseDto toCategoriaSaveResponseDto(Categoria categoria);

}


