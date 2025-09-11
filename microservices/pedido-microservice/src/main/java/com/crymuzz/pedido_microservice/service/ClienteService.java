package com.crymuzz.pedido_microservice.service;



import com.crymuzz.pedido_microservice.model.dto.CreateClienteDTO;
import com.crymuzz.pedido_microservice.model.dto.ResponseClienteDTO;
import com.crymuzz.pedido_microservice.model.dto.UpdateClienteDTO;
import com.crymuzz.pedido_microservice.model.entity.Cliente;

import java.util.List;

public interface ClienteService {
    ResponseClienteDTO crearCliente(CreateClienteDTO createClienteDTO);
    List<ResponseClienteDTO> listarClientes();
    ResponseClienteDTO obtenerClienteId(Long id);
    Cliente obtenerEntidadId(Long id);
    ResponseClienteDTO actualizarCliente(Long id, UpdateClienteDTO updateClienteDTO);
    void eliminarCliente(Long id);
}
