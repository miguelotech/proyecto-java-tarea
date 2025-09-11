package com.crymuzz.producto_microservice.mapper;

import com.crymuzz.producto_microservice.model.dto.CreateProductoDTO;
import com.crymuzz.producto_microservice.model.dto.ResponseProductDTO;
import com.crymuzz.producto_microservice.model.entity.Producto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoriaMapper.class})
public interface ProductoMapper {

    Producto toEntity(CreateProductoDTO dto);

    ResponseProductDTO toDto(Producto producto);

    List<ResponseProductDTO> toDtoList(List<Producto> productos);
}
