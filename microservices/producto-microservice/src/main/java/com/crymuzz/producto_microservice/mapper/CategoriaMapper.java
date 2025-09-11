package com.crymuzz.producto_microservice.mapper;

import com.crymuzz.producto_microservice.model.dto.CreateCategoriaDTO;
import com.crymuzz.producto_microservice.model.dto.ResponseCategoriaDTO;
import com.crymuzz.producto_microservice.model.entity.Categoria;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    Categoria toEntity(CreateCategoriaDTO dto);

    ResponseCategoriaDTO toDto(Categoria categoria);

    List<ResponseCategoriaDTO> toDtoList(List<Categoria> categorias);
}