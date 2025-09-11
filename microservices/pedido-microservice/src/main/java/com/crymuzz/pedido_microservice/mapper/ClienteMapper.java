package com.crymuzz.pedido_microservice.mapper;


import com.crymuzz.pedido_microservice.model.dto.CreateClienteDTO;
import com.crymuzz.pedido_microservice.model.dto.ResponseClienteDTO;
import com.crymuzz.pedido_microservice.model.entity.Cliente;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    Cliente toEntity(CreateClienteDTO dto);
    ResponseClienteDTO toDto(Cliente cliente);
    List<ResponseClienteDTO> toDtoList(List<Cliente> clientes);
}
