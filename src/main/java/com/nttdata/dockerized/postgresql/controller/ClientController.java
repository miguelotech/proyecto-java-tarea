package com.nttdata.dockerized.postgresql.controller;

import com.nttdata.dockerized.postgresql.model.dto.ClientDto;
import com.nttdata.dockerized.postgresql.model.dto.ClientSaveRequestDto;
import com.nttdata.dockerized.postgresql.model.dto.ClientSaveResponseDto;
import com.nttdata.dockerized.postgresql.model.dto.ClientUpdateRequestDto;
import com.nttdata.dockerized.postgresql.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.nttdata.dockerized.postgresql.mapper.ClientMapper.INSTANCE;

/**
 * Controlador REST para gestión de clientes
 * Endpoints: GET, POST, PUT, DELETE /api/clients
 */
@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    // Listar todos los clientes
    @GetMapping
    public List<ClientDto> getAllClients() {
        return clientService.listAll().stream()
                .map(INSTANCE::map)
                .toList();
    }

    // Obtener cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable final Long id) {
        return clientService.findById(id)
                .map(INSTANCE::map)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear nuevo cliente
    @PostMapping
    public ClientSaveResponseDto save(@RequestBody final ClientSaveRequestDto clientSaveRequestDto) {
        return INSTANCE.toClientSaveResponseDto(
                clientService.save(INSTANCE.toEntity(clientSaveRequestDto))
        );
    }

    // Actualizar cliente existente
    @PutMapping("/{id}")
    public ResponseEntity<ClientSaveResponseDto> update(@PathVariable final Long id, 
                                                       @RequestBody final ClientUpdateRequestDto clientUpdateRequestDto) {
        return clientService.update(id, INSTANCE.toEntity(clientUpdateRequestDto))
                .map(INSTANCE::toClientSaveResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        return clientService.deleteById(id) 
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
