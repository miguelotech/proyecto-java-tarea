package com.crymuzz.pedido_microservice.service.impl;


import com.crymuzz.pedido_microservice.exception.BadRequestException;
import com.crymuzz.pedido_microservice.exception.ResourceNotFoundException;
import com.crymuzz.pedido_microservice.mapper.ClienteMapper;
import com.crymuzz.pedido_microservice.model.dto.CreateClienteDTO;
import com.crymuzz.pedido_microservice.model.dto.ResponseClienteDTO;
import com.crymuzz.pedido_microservice.model.dto.UpdateClienteDTO;
import com.crymuzz.pedido_microservice.model.entity.Cliente;
import com.crymuzz.pedido_microservice.repository.ClienteRepository;
import com.crymuzz.pedido_microservice.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Override
    @Transactional
    public ResponseClienteDTO crearCliente(CreateClienteDTO createClienteDTO) {
        boolean existe = clienteRepository.existsByEmail(createClienteDTO.getEmail());
        if (existe)
            throw new BadRequestException("Ya existe un cliente con ese email");

        Cliente cliente = clienteMapper.toEntity(createClienteDTO);
        cliente.setFechaRegistro(LocalDate.now());
        cliente.setActivo(true);

        cliente = clienteRepository.save(cliente);
        return clienteMapper.toDto(cliente);
    }

    @Override
    @Transactional
    public List<ResponseClienteDTO> listarClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clienteMapper.toDtoList(clientes);
    }

    @Override
    @Transactional
    public ResponseClienteDTO obtenerClienteId(Long id) {
        Cliente cliente = obtenerEntidadId(id);
        return clienteMapper.toDto(cliente);
    }

    @Override
    @Transactional
    public Cliente obtenerEntidadId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
    }

    @Override
    @Transactional
    public ResponseClienteDTO actualizarCliente(Long id, UpdateClienteDTO updateClienteDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        cliente.setNombre(updateClienteDTO.getNombre());
        cliente.setEmail(updateClienteDTO.getEmail());

        Cliente actualizado = clienteRepository.save(cliente);
        return clienteMapper.toDto(actualizado);
    }

    @Override
    @Transactional
    public void eliminarCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        clienteRepository.delete(cliente);
    }

}
